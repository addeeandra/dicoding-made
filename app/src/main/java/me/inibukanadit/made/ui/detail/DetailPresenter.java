package me.inibukanadit.made.ui.detail;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.made.data.remote.MovieDbApi;
import me.inibukanadit.made.data.remote.model.Movie;
import me.inibukanadit.made.ui.base.BasePresenter;
import me.inibukanadit.sharedmodule.db.DatabaseContract;

import static me.inibukanadit.sharedmodule.db.DatabaseContract.CONTENT_URI;

public class DetailPresenter extends BasePresenter<DetailView> {

    private final ContentResolver mContentResolver;

    public DetailPresenter(ContentResolver contentResolver,
                           MovieDbApi mMovieDbApi,
                           CompositeDisposable mCompositeDisposable) {
        super(mMovieDbApi, mCompositeDisposable);
        mContentResolver = contentResolver;
    }

    public void onMoviePassed(Movie movie) {
        getView().showMovie(movie);
    }

    public void addToFavorite(Movie movie) {
        Cursor cursor = mContentResolver.query(
                CONTENT_URI
                        .buildUpon()
                        .appendPath(String.valueOf(movie.getId()))
                        .build(),
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() < 1) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.FavoritesColumn._ID, movie.getId());
            cv.put(DatabaseContract.FavoritesColumn.TITLE, movie.getTitle());
            cv.put(DatabaseContract.FavoritesColumn.DESCRIPTION, movie.getOverview());
            cv.put(DatabaseContract.FavoritesColumn.DATE, movie.getReleaseDate());

            mContentResolver.insert(CONTENT_URI, cv);
            getView().showMessage("Added to favorite");
        }
    }

}
