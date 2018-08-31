package me.inibukanadit.made.ui.detail;

import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.made.data.MovieDbApi;
import me.inibukanadit.made.data.model.Movie;
import me.inibukanadit.made.ui.base.BasePresenter;

public class DetailPresenter extends BasePresenter<DetailView> {

    public DetailPresenter(MovieDbApi mMovieDbApi, CompositeDisposable mCompositeDisposable) {
        super(mMovieDbApi, mCompositeDisposable);
    }

    public void onMoviePassed(Movie movie) {
        getView().showMovie(movie);
    }

}
