package me.inibukanadit.sharedmodule.remote;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.inibukanadit.sharedmodule.BuildConfig;
import me.inibukanadit.sharedmodule.remote.model.Movie;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDbApi {

    private static final String HOST = "api.themoviedb.org";
    private static final String IMAGE_HOST = "image.tmdb.org";
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String DEFAULT_LANG = "en-US";

    private MovieDbService mService;

    public MovieDbApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        mService = new Retrofit
                .Builder()
                .client(client)
                .baseUrl("http://" + HOST + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MovieDbService.class);
    }

    public Observable<List<Movie>> search(String keyword) {
        return mService
                .search(API_KEY, DEFAULT_LANG, keyword)
                .subscribeOn(Schedulers.io())
                .map(new Function<MovieResponse, List<Movie>>() {
                    @Override
                    public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                        return movieResponse.results;
                    }
                });
    }

    public Observable<List<Movie>> upcoming() {
        return mService
                .upcoming(API_KEY, DEFAULT_LANG)
                .subscribeOn(Schedulers.io())
                .map(new Function<MovieResponse, List<Movie>>() {
                    @Override
                    public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                        return movieResponse.results;
                    }
                });
    }

    public Observable<List<Movie>> nowPlaying() {
        return mService
                .nowPlaying(API_KEY, DEFAULT_LANG)
                .subscribeOn(Schedulers.io())
                .map(new Function<MovieResponse, List<Movie>>() {
                    @Override
                    public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                        return movieResponse.results;
                    }
                });
    }

    public static String getPosterUrl(String path) {
        return "http://" + IMAGE_HOST + "/t/p/w185" + path;
    }

}
