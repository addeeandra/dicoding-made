package me.inibukanadit.made.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.inibukanadit.made.R;
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

    private MainPagerAdapter mPageAdapter;
    private List<Fragment> mPages;
    private List<String> mTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            mPages = new ArrayList<>();

            MoviesFragment nowPlayingFragment = new MoviesFragment();
            nowPlayingFragment.setParentView(this);
            nowPlayingFragment.setFetchType(MoviesFragment.FETCH_NOW_PLAYING);
            mPages.add(nowPlayingFragment);

            MoviesFragment upcomingFragment = new MoviesFragment();
            upcomingFragment.setParentView(this);
            upcomingFragment.setFetchType(MoviesFragment.FETCH_UPCOMING);
            mPages.add(upcomingFragment);

            mTitles = new ArrayList<>();
            mTitles.add(getString(R.string.now_playing));
            mTitles.add(getString(R.string.upcoming));

            mPageAdapter = new MainPagerAdapter(getSupportFragmentManager(), getPages(), getTitles());
            vpMain.setAdapter(mPageAdapter);
            tabMain.setupWithViewPager(vpMain);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent searchIntent = new Intent(this, SearchActivity.class);
            startActivity(searchIntent);
            return true;
        } else if (item.getItemId() == R.id.action_change_language) {
            Intent settingIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(settingIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public List<Fragment> getPages() {
        return mPages;
    }

    @Override
    public List<String> getTitles() {
        return mTitles;
    }

}
