package me.inibukanadit.made.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse<T> {

    @SerializedName("page")
    int page;

    @SerializedName("total_results")
    int totalResults;

    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("results")
    List<T> results;

}
