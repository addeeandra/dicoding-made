package me.inibukanadit.made.utils;

import android.os.Bundle;

import me.inibukanadit.made.data.model.Movie;

public class Mapper {

    public static Bundle movieToBundle(Movie movie) {
        Bundle data = new Bundle();
        data.putString("title", movie.getTitle());
        data.putString("date", movie.getReleaseDate());
        data.putString("overview", movie.getOverview());
        data.putString("backdrop", movie.getBackdrop());
        data.putString("poster", movie.getPoster());
        data.putDouble("popularity", movie.getPopularityRate());
        data.putDouble("vote", movie.getVoteAverageRate());

        return data;
    }

    public static Movie bundleToMovie(Bundle data) {
        return new Movie(
                0,
                data.getString("title"),
                data.getString("overview"),
                data.getString("date"),
                data.getString("poster"),
                data.getString("backdrop"),
                data.getDouble("popularity"),
                data.getDouble("vote")
        );
    }

}
