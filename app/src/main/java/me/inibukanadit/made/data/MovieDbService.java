package me.inibukanadit.made.data;

import io.reactivex.Observable;
import me.inibukanadit.made.data.model.Movie;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDbService {

    @GET("/3/search/movie")
    Observable<SearchResponse<Movie>> search(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query
    );

}
