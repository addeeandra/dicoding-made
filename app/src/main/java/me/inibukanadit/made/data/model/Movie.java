package me.inibukanadit.made.data.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie {

    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("backdrop_path")
    private String backdrop;

    @SerializedName("vote_average")
    private Double voteAverageRate;

    @SerializedName("popularity")
    private Double popularityRate;

    public Movie() { }

    public Movie(long id, String title, String overview, String releaseDate, String poster, String backdrop, Double voteAverageRate, Double popularityRate) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.backdrop = backdrop;
        this.voteAverageRate = voteAverageRate;
        this.popularityRate = popularityRate;
    }

    private String formatDate(String fromDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date fromDateObj = sdf.parse(fromDate);

            sdf.applyPattern("E, dd MMM, yyyy");

            return sdf.format(fromDateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fromDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return formatDate(releaseDate);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public Double getVoteAverageRate() {
        return voteAverageRate;
    }

    public void setVoteAverageRate(Double voteAverageRate) {
        this.voteAverageRate = voteAverageRate;
    }

    public Double getPopularityRate() {
        return popularityRate;
    }

    public void setPopularityRate(Double popularityRate) {
        this.popularityRate = popularityRate;
    }
}
