package me.inibukanadit.favoritemoviesapp.favorites;

import java.util.List;

import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.MvpView;

interface FavoritesView extends MvpView {

    void showMovies(List<Movie> movieList);

    void showLoading();

    void hideLoading();

    void showPlaceholder();

}
