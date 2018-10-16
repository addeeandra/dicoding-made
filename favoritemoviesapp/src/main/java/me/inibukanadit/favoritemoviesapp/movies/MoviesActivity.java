package me.inibukanadit.favoritemoviesapp.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MoviesActivity extends BaseActivity implements MoviesView {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MoviesPresenter mPresenter;

    @BindView(R.id.rv_movies)
    RecyclerView listMovies;

    @BindView(R.id.clp_movies)
    ContentLoadingProgressBar clpMovies;

    @BindView(R.id.ph_movies)
    TextView phMovies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);

        showPlaceholder();
        hideLoading();

        mPresenter = new MoviesPresenter(mCompositeDisposable, getContentResolver());
        mPresenter.onAttach(this);

        mPresenter.fetchFavoriteMovies();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public void showMovies(final List<Movie> movieList) {
        listMovies.setVisibility(View.VISIBLE);
        listMovies.setLayoutManager(new LinearLayoutManager(this));
        listMovies.setAdapter(new MoviesAdapter(movieList, new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onDetailClick(Movie movie) {
                Bundle data = new Bundle();
                data.putParcelable("movie", movie);

                Intent detailIntent = new Intent(MoviesActivity.this, DetailActivity.class);

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
        clpMovies.hide();
    }

    @Override
    public void showPlaceholder() {
        phMovies.setVisibility(View.VISIBLE);
        listMovies.setVisibility(View.GONE);
    }

}
