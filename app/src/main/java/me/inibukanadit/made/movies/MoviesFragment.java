package me.inibukanadit.made.movies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.made.detail.DetailActivity;
import me.inibukanadit.made.R;
import me.inibukanadit.sharedmodule.remote.MovieDbApi;
import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.BaseFragment;

public class MoviesFragment extends BaseFragment implements MoviesView {

    public static final int FETCH_UPCOMING = 1;
    public static int FETCH_NOW_PLAYING = 2;

    private MovieDbApi mApi = new MovieDbApi();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MoviesPresenter mPresenter = new MoviesPresenter(mApi, mCompositeDisposable);

    private int mFetchType = 0;

    @BindView(R.id.rv_movies)
    RecyclerView listMovies;

    @BindView(R.id.clp_movies)
    ContentLoadingProgressBar clpMovies;

    @BindView(R.id.ph_movies)
    TextView phMovies;

    @BindView(R.id.swipe_refresh_movies)
    SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        showPlaceholder();
        hideLoading();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMovies();
            }
        });

        fetchMovies();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.onDetach();
    }

    @Override
    public void showMovies(final List<Movie> movieList) {
        swipeRefresh.setRefreshing(false);

        listMovies.setVisibility(View.VISIBLE);
        listMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMovies.setAdapter(new MoviesAdapter(movieList, new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onDetailClick(Movie movie) {
                Bundle data = new Bundle();
                data.putParcelable("movie", movie);

                Intent detailIntent = new Intent(getActivity(), DetailActivity.class);

                detailIntent.putExtras(data);
                startActivity(detailIntent);
            }

            @Override
            public void onShareclick(Movie movie) {
                // TODO WOI! KUDU ISOK NGESHARE HAYO
                getParentView().showMessage("Share " + movie.getTitle());
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

    private void fetchMovies() {
        if (mFetchType == FETCH_UPCOMING) {
            mPresenter.fetchNowPlayingMovies();
        } else if (mFetchType == FETCH_NOW_PLAYING) {
            mPresenter.fetchUpcomingMovies();
        }
    }

    public void setFetchType(int type) {
        mFetchType = type;
    }

    public MoviesPresenter getPresenter() {
        return mPresenter;
    }

}
