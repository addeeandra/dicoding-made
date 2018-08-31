package me.inibukanadit.made.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.inibukanadit.made.data.model.Movie;

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
