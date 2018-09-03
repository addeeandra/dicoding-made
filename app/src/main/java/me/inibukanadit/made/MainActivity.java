package me.inibukanadit.made;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.DrawerLayout;

import me.inibukanadit.made.ui.SearchesFragment;

public class MainActivity extends AppCompatActivity
        implements MainDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private MainDrawerFragment mNavigationDrawerFragment;
    private SearchesFragment mEnglishTranslateFragment;
    private SearchesFragment mIndonesiaTranslateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (MainDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (position == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, getEnglishPage())
                    .commit();
        } else if (position == 1) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, getIndonesiaPage())
                    .commit();
        }
    }

    private SearchesFragment getEnglishPage() {
        if (mEnglishTranslateFragment == null) {
            mEnglishTranslateFragment = new SearchesFragment();
            mEnglishTranslateFragment.setLanguage(SearchesFragment.Language.ENGLISH);
        }
        return mEnglishTranslateFragment;
    }

    private SearchesFragment getIndonesiaPage() {
        if (mIndonesiaTranslateFragment == null) {
            mIndonesiaTranslateFragment = new SearchesFragment();
            mIndonesiaTranslateFragment.setLanguage(SearchesFragment.Language.INDONESIA);
        }
        return mIndonesiaTranslateFragment;
    }

}
