package me.inibukanadit.made.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.made.R;
import me.inibukanadit.made.data.MovieDbApi;
import me.inibukanadit.made.ui.base.BaseActivity;
import me.inibukanadit.made.ui.movies.MoviesFragment;
import me.inibukanadit.made.ui.movies.MoviesPresenter;

public class SearchActivity extends BaseActivity implements SearchView {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MovieDbApi mMovieDbApi = new MovieDbApi();
    private SearchPresenter mPresenter = new SearchPresenter(mMovieDbApi, mCompositeDisposable);

    private MoviesFragment mMoviesFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.sv_search_input)
    android.support.v7.widget.SearchView svSearchInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            mMoviesFragment = new MoviesFragment();
            mMoviesFragment.setParentView(this);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fr_search_content, mMoviesFragment)
                    .commit();
        }

        svSearchInput.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.onSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mPresenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public MoviesPresenter getMoviesPresenter() {
        return mMoviesFragment.getPresenter();
    }
}
