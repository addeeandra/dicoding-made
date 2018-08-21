package me.inibukanadit.made;

import java.util.List;

import me.inibukanadit.made.data.model.Movie;

public interface MainView {

    void showMovies(List<Movie> movieList);

    void showLoading();

    void hideLoading();

    void showPlaceholder();

}
