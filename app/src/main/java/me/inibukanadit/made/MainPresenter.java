package me.inibukanadit.made;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.inibukanadit.made.data.MovieDbApi;
import me.inibukanadit.made.data.model.Movie;

public class MainPresenter {

    private MainView mView;
    private MovieDbApi mApi;

    private Disposable mMovieListDisposable;

    public MainPresenter(MainView view, MovieDbApi api) {
        mView = view;
        mApi = api;
    }

    public void fetchMovieList(String keyword) {
        mView.showLoading();

        mApi
                .search(keyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mMovieListObserver);
    }

    private Observer<List<Movie>> mMovieListObserver = new Observer<List<Movie>>() {
        @Override
        public void onSubscribe(Disposable d) {
            if (mMovieListDisposable != null) {
                mMovieListDisposable.dispose();
            }

            mMovieListDisposable = d;
        }

        @Override
        public void onNext(List<Movie> movieList) {
            mView.showMovies(movieList);
            mView.hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            mView.showPlaceholder();
            mView.hideLoading();
        }

        @Override
        public void onComplete() { }
    };

}
