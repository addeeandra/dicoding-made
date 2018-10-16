package me.inibukanadit.favoritemoviesapp.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.favoritemoviesapp.R;
import me.inibukanadit.favoritemoviesapp.detail.DetailActivity;
import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.BaseActivity;

public class FavoritesActivity extends BaseActivity implements FavoritesView {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private FavoritesPresenter mPresenter;

    @BindView(R.id.rv_movies)
    RecyclerView listMovies;

    @BindView(R.id.clp_movies)
    ContentLoadingProgressBar clpMovies;

    @BindView(R.id.ph_movies)
    TextView phMovies;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh_movies)
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        showPlaceholder();
        hideLoading();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchFavoriteMovies();
            }
        });

        mPresenter = new FavoritesPresenter(mCompositeDisposable, getContentResolver());
        mPresenter.onAttach(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.fetchFavoriteMovies();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.language, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_language) {
            Intent settingIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(settingIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMovies(final List<Movie> movieList) {
        listMovies.setVisibility(View.VISIBLE);
        listMovies.setLayoutManager(new LinearLayoutManager(this));
        listMovies.setAdapter(new FavoritesAdapter(movieList, new FavoritesAdapter.OnMovieClickListener() {
            @Override
            public void onDetailClick(Movie movie) {
                Bundle data = new Bundle();
                data.putParcelable("movie", movie);

                Intent detailIntent = new Intent(FavoritesActivity.this, DetailActivity.class);

                detailIntent.putExtras(data);
                startActivity(detailIntent);
            }

            @Override
            public void onShareclick(Movie movie) {
                showMessage("Share " + movie.getTitle());
            }
        }));
    }

    @Override
    public void showLoading() {
        clpMovies.show();
        phMovies.setVisibility(View.GONE);
        listMovies.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
        clpMovies.hide();
    }

    @Override
    public void showPlaceholder() {
        phMovies.setVisibility(View.VISIBLE);
        listMovies.setVisibility(View.GONE);
    }

}
