package me.inibukanadit.made.movies;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.inibukanadit.sharedmodule.remote.MovieDbApi;
import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.BasePresenter;

public class MoviesPresenter extends BasePresenter<MoviesView> {

    MoviesPresenter(MovieDbApi mMovieDbApi, CompositeDisposable mCompositeDisposable) {
        super(mMovieDbApi, mCompositeDisposable);
    }

    public void fetchNowPlayingMovies() {
        getView().showLoading();
        getCompositeDisposable().add(fetchMovies(getMovieDbApi().nowPlaying()));
    }

    public void fetchUpcomingMovies() {
        getView().showLoading();
        getCompositeDisposable().add(fetchMovies(getMovieDbApi().upcoming()));
    }

    public void searchMovies(String keyword) {
        getView().showLoading();
        getCompositeDisposable().add(fetchMovies(getMovieDbApi().search(keyword)));
    }

    private Disposable fetchMovies(Observable<List<Movie>> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) {
                        getView().showMovies(movies);
                        getView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().showPlaceholder();
                        getView().hideLoading();
                    }
                });
    }

}