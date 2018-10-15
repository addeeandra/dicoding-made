package me.inibukanadit.sharedmodule.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.inibukanadit.sharedmodule.remote.model.Movie;

public class MovieResponse {

    @SerializedName("page")
    int page;

    @SerializedName("total_results")
    int totalResults;

    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("results")
    List<Movie> results;

}
