package me.inibukanadit.made.ui.search;

import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.made.data.MovieDbApi;
import me.inibukanadit.made.ui.base.BasePresenter;

public class SearchPresenter extends BasePresenter<SearchView> {

    public SearchPresenter(MovieDbApi mMovieDbApi, CompositeDisposable mCompositeDisposable) {
        super(mMovieDbApi, mCompositeDisposable);
    }

    public void onSearch(String query) {
        getView()
                .getMoviesPresenter()
                .searchMovies(query);
    }

}
