package me.inibukanadit.made.utils;

import android.os.Bundle;

import me.inibukanadit.made.data.remote.model.Movie;

public class Mapper {

    public static Bundle movieToBundle(Movie movie) {
        Bundle data = new Bundle();
        data.putParcelable("movie", movie);
        return data;
    }

    public static Movie bundleToMovie(Bundle data) {
        return data.getParcelable("movie");
    }

}
