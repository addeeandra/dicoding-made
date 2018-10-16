package me.inibukanadit.favoritemoviesapp.movies;

import android.content.ContentResolver;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.BasePresenter;

import static me.inibukanadit.sharedmodule.db.DatabaseContract.CONTENT_URI;

class MoviesPresenter extends BasePresenter<MoviesView> {

    private final ContentResolver mContentResolver;

    MoviesPresenter(
            CompositeDisposable compositeDisposable,
            ContentResolver contentResolver) {
        super(compositeDisposable);
        mContentResolver = contentResolver;
    }

    public void fetchFavoriteMovies() {
        getView().showLoading();
        getCompositeDisposable().add(Single.just(1)
                .map(new Function<Integer, Cursor>() {
                    @Override
                    public Cursor apply(Integer integer) {
                        return mContentResolver.query(CONTENT_URI, null, null, null, null);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Cursor>() {
                    @Override
                    public void accept(Cursor cursor) {
                        List<Movie> movies = new ArrayList<>();

                        if (cursor.moveToFirst()) {
                            Movie movie;
                            do {
                                movie = new Movie();
                                movie.setId(cursor.getInt(cursor.getColumnIndex("_ID")));
                                movie.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
                                movie.setOverview(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
                                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex("DATE")));

                                movies.add(movie);
                            } while (cursor.moveToNext());
                        }

                        getView().showMovies(movies);
                        getView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getView().showPlaceholder();
                        getView().hideLoading();
                    }
                }));
    }

}