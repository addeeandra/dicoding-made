package me.inibukanadit.made.detail;

import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.MvpView;

interface DetailView extends MvpView {

    void showMovie(Movie movie);

    void showAddToFavorite();

    void showRemoveFromFavorite();

}
