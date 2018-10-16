package me.inibukanadit.made.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import me.inibukanadit.made.R;
import me.inibukanadit.made.detail.DetailActivity;
import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.BaseFragment;

public class FavoritesFragment extends BaseFragment implements FavoritesView {

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private FavoritesPresenter mPresenter;

    @BindView(R.id.rv_movies)
    RecyclerView listMovies;

    @BindView(R.id.clp_movies)
    ContentLoadingProgressBar clpMovies;

    @BindView(R.id.ph_movies)
    TextView phMovies;

    @BindView(R.id.swipe_refresh_movies)
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new FavoritesPresenter(mCompositeDisposable, getContext().getContentResolver());
        mPresenter.onAttach(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_favorites, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        showPlaceholder();
        hideLoading();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.fetchFavoriteMovies();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.fetchFavoriteMovies();
    }

    @Override
    public void showMovies(final List<Movie> movieList) {
        listMovies.setVisibility(View.VISIBLE);
        listMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMovies.setAdapter(new FavoritesAdapter(movieList, new FavoritesAdapter.OnMovieClickListener() {
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
