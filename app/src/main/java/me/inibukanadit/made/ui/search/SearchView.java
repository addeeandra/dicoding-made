package me.inibukanadit.made.ui.search;

import me.inibukanadit.made.ui.base.MvpView;
import me.inibukanadit.made.ui.movies.MoviesPresenter;

public interface SearchView extends MvpView {

    MoviesPresenter getMoviesPresenter();

}
