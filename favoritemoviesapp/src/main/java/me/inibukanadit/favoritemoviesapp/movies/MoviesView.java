package me.inibukanadit.favoritemoviesapp.movies;

import java.util.List;

import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.MvpView;

public interface MoviesView extends MvpView {

    void showMovies(List<Movie> movieList);

    void showLoading();

    void hideLoading();

    void showPlaceholder();

}
