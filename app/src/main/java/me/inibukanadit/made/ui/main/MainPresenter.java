package me.inibukanadit.made.ui.main;

import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.made.data.remote.MovieDbApi;
import me.inibukanadit.made.ui.base.BasePresenter;

public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MovieDbApi mMovieDbApi, CompositeDisposable mCompositeDisposable) {
        super(mMovieDbApi, mCompositeDisposable);
    }

}
