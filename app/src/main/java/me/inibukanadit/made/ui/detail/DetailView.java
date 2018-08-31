package me.inibukanadit.made.ui.detail;

import me.inibukanadit.made.data.model.Movie;
import me.inibukanadit.made.ui.base.MvpView;

public interface DetailView extends MvpView {

    void showMovie(Movie movie);

}
