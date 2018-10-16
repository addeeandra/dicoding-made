package me.inibukanadit.made.search;

import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.sharedmodule.remote.MovieDbApi;
import me.inibukanadit.sharedmodule.ui.BasePresenter;

class SearchPresenter extends BasePresenter<SearchView> {

    public SearchPresenter(MovieDbApi mMovieDbApi, CompositeDisposable mCompositeDisposable) {
        super(mMovieDbApi, mCompositeDisposable);
    }

    public void onSearch(String query) {
        getView()
                .getMoviesPresenter()
                .searchMovies(query);
    }

}
