package me.inibukanadit.sharedmodule.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie implements Parcelable {

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

    public Movie() {
    }

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

    protected Movie(Parcel in) {
        id = in.readLong();
        title = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        poster = in.readString();
        backdrop = in.readString();
        if (in.readByte() == 0) {
            voteAverageRate = null;
        } else {
            voteAverageRate = in.readDouble();
        }
        if (in.readByte() == 0) {
            popularityRate = null;
        } else {
            popularityRate = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(poster);
        dest.writeString(backdrop);
        if (voteAverageRate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(voteAverageRate);
        }
        if (popularityRate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(popularityRate);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private String formatDate(String fromDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date fromDateObj = sdf.parse(fromDate);

            sdf.applyPattern("E, dd MMM, yyyy");

            return sdf.format(fromDateObj);
        } catch (ParseException e) { /* IGNORE */ }

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

    public String getRawReleaseDate() {
        return releaseDate;
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
