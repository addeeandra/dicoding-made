package me.inibukanadit.made.movies;

import java.util.List;

import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.MvpView;

interface MoviesView extends MvpView {

    void showMovies(List<Movie> movieList);

    void showLoading();

    void hideLoading();

    void showPlaceholder();

}
