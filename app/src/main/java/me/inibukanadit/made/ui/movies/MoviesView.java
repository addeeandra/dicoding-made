package me.inibukanadit.made.ui.movies;

import java.util.List;

import me.inibukanadit.made.data.model.Movie;
import me.inibukanadit.made.ui.base.MvpView;

public interface MoviesView extends MvpView {

    void showMovies(List<Movie> movieList);

    void showLoading();

    void hideLoading();

    void showPlaceholder();

}
