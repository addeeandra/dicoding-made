package me.inibukanadit.made.search;


import me.inibukanadit.made.movies.MoviesPresenter;
import me.inibukanadit.sharedmodule.ui.MvpView;

interface SearchView extends MvpView {

    MoviesPresenter getMoviesPresenter();

}
