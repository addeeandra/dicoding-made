package me.inibukanadit.made.main;

import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.sharedmodule.remote.MovieDbApi;
import me.inibukanadit.sharedmodule.ui.BasePresenter;

class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MovieDbApi mMovieDbApi, CompositeDisposable mCompositeDisposable) {
        super(mMovieDbApi, mCompositeDisposable);
    }

}
