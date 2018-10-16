package me.inibukanadit.sharedmodule.ui;

import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.sharedmodule.remote.MovieDbApi;

public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mView;

    private final MovieDbApi mMovieDbApi;
    private final CompositeDisposable mCompositeDisposable;

    protected BasePresenter(CompositeDisposable mCompositeDisposable) {
        this.mMovieDbApi = null;
        this.mCompositeDisposable = mCompositeDisposable;
    }

    public BasePresenter(MovieDbApi mMovieDbApi, CompositeDisposable mCompositeDisposable) {
        this.mMovieDbApi = mMovieDbApi;
        this.mCompositeDisposable = mCompositeDisposable;
    }

    @Override
    public void onAttach(V view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        mView = null;
    }

    public V getView() {
        return mView;
    }

    public MovieDbApi getMovieDbApi() {
        return mMovieDbApi;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

}
