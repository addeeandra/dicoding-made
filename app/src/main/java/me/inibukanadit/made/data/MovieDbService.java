package me.inibukanadit.made.data;

import io.reactivex.Observable;
import me.inibukanadit.made.data.model.Movie;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDbService {

    @GET("/3/search/movie")
    Observable<MovieResponse> search(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query
    );

    @GET("/3/movie/upcoming")
    Observable<MovieResponse> upcoming(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("/3/movie/now_playing")
    Observable<MovieResponse> nowPlaying(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

}
