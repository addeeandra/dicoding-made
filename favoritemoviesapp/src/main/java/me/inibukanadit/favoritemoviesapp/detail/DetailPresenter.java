package me.inibukanadit.favoritemoviesapp.detail;

import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.sharedmodule.remote.MovieDbApi;
import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.BasePresenter;

public class DetailPresenter extends BasePresenter<DetailView> {

    public DetailPresenter(MovieDbApi mMovieDbApi, CompositeDisposable mCompositeDisposable) {
        super(mMovieDbApi, mCompositeDisposable);
    }

    public void onMoviePassed(Movie movie) {
        getView().showMovie(movie);
    }

}
