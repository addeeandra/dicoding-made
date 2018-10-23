package me.inibukanadit.made.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.inibukanadit.made.R;
import me.inibukanadit.made.favorites.FavoritesFragment;
import me.inibukanadit.made.main.reminder.ReminderPreference;
import me.inibukanadit.made.main.reminder.ReminderReceiver;
import me.inibukanadit.made.movies.MoviesFragment;
import me.inibukanadit.made.search.SearchActivity;
import me.inibukanadit.sharedmodule.ui.BaseActivity;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tab_main)
    TabLayout tabMain;

    @BindView(R.id.vp_main)
    ViewPager vpMain;

    private static final int RC_LANGUAGE = 22;

    private ReminderReceiver mReminderReceiver;
    private ReminderPreference mReminderPreference;

    private MainPagerAdapter mPageAdapter;
    private List<Fragment> mPages;
    private List<String> mTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        /**
         * SETUP FRAGMENTS
         */
        mPages = new ArrayList<>();

        FavoritesFragment favoritesFragment = new FavoritesFragment();
        favoritesFragment.setParentView(this);
        mPages.add(favoritesFragment);

        MoviesFragment nowPlayingFragment = new MoviesFragment();
        nowPlayingFragment.setParentView(this);
        nowPlayingFragment.setFetchType(MoviesFragment.FETCH_NOW_PLAYING);
        mPages.add(nowPlayingFragment);

        MoviesFragment upcomingFragment = new MoviesFragment();
        upcomingFragment.setParentView(this);
        upcomingFragment.setFetchType(MoviesFragment.FETCH_UPCOMING);
        mPages.add(upcomingFragment);

        mTitles = new ArrayList<>();
        mTitles.add("");
        mTitles.add(getString(R.string.now_playing));
        mTitles.add(getString(R.string.upcoming));

        mPageAdapter = new MainPagerAdapter(getSupportFragmentManager(), getPages(), getTitles());
        vpMain.setOffscreenPageLimit(3);
        vpMain.setAdapter(mPageAdapter);
        tabMain.setupWithViewPager(vpMain);

        int tabFavIndex = 0;
        View favoriteTab = ((LinearLayout) tabMain.getChildAt(0)).getChildAt(tabFavIndex);
        LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) favoriteTab.getLayoutParams();

        tabMain.getTabAt(tabFavIndex).setIcon(R.drawable.ic_star_filled);

        lParams.weight = 0;
        lParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        favoriteTab.setLayoutParams(lParams);

        vpMain.setCurrentItem(1);

        /**
         * SETUP REMINDER
         */
        mReminderReceiver = new ReminderReceiver();
        mReminderPreference = new ReminderPreference(getApplicationContext());

        setReminderDaily(mReminderPreference.isReminderDailyActive());
        setReminderRelease(mReminderPreference.isReminderReleaseActive());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuDailyReminder = menu.findItem(R.id.action_toggle_daily_reminder);
        menuDailyReminder.setTitle(
                mReminderPreference.isReminderDailyActive() ?
                        R.string.turn_off_daily_reminder : R.string.turn_on_daily_reminder);

        MenuItem menuReleaseReminder = menu.findItem(R.id.action_toggle_release_reminder);
        menuReleaseReminder.setTitle(
                mReminderPreference.isReminderReleaseActive() ?
                        R.string.turn_off_release_reminder : R.string.turn_on_release_reminder);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case R.id.action_change_language:
                Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivityForResult(languageIntent, RC_LANGUAGE);
                return true;
            case R.id.action_toggle_daily_reminder:
                boolean isActiveDaily = mReminderPreference.toggleReminderDaily();
                setReminderDaily(isActiveDaily);
                return true;
            case R.id.action_toggle_release_reminder:
                boolean isActiveRelease = mReminderPreference.toggleReminderRelease();
                setReminderRelease(isActiveRelease);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // ENSURE THAT ACTIVITY RESTARTED WHEN GET RESULT FROM LANGUAGE SETTINGS TO REFRESH
        if (requestCode == RC_LANGUAGE) {
            Intent restartIntent = new Intent(this, MainActivity.class);
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(restartIntent);
        }
    }

    @Override
    public List<Fragment> getPages() {
        return mPages;
    }

    @Override
    public List<String> getTitles() {
        return mTitles;
    }

    private void setReminderDaily(boolean active) {
        if (mReminderPreference.isReminderDailyActive() != active)
            mReminderPreference.setReminderDaily(active);

        if (active) {
            mReminderReceiver.setRepeatingReminder(this, ReminderReceiver.TYPE_DAILY, "We miss you :(");
        } else {
            mReminderReceiver.cancelReminder(this, ReminderReceiver.TYPE_DAILY);
        }

        invalidateOptionsMenu();
    }

    private void setReminderRelease(boolean active) {
        if (mReminderPreference.isReminderReleaseActive() != active)
            mReminderPreference.setReminderRelease(active);

        if (active) {
            mReminderReceiver.setRepeatingReminder(this, ReminderReceiver.TYPE_RELEASE, "New movie release!");
        } else {
            mReminderReceiver.cancelReminder(this, ReminderReceiver.TYPE_RELEASE);
        }

        invalidateOptionsMenu();
    }

}
