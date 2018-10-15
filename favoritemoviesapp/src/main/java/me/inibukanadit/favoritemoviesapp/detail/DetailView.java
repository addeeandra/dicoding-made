package me.inibukanadit.favoritemoviesapp.detail;

import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.MvpView;

public interface DetailView extends MvpView {

    void showMovie(Movie movie);

}
