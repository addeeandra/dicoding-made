package me.inibukanadit.favoritemoviesapp.detail;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.sharedmodule.db.DatabaseContract;
import me.inibukanadit.sharedmodule.remote.MovieDbApi;
import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.BasePresenter;

import static me.inibukanadit.sharedmodule.db.DatabaseContract.CONTENT_URI;

class DetailPresenter extends BasePresenter<DetailView> {

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

    public void checkFavorite(Movie movie, boolean toggle) {
        Uri detailUri = CONTENT_URI
                .buildUpon()
                .appendPath(String.valueOf(movie.getId()))
                .build();

        Cursor cursor = mContentResolver.query(
                detailUri,
                null,
                null,
                null,
                null
        );

        if (cursor.getCount() < 1) {
            if (toggle) {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseContract.FavoritesColumn._ID, movie.getId());
                cv.put(DatabaseContract.FavoritesColumn.TITLE, movie.getTitle());
                cv.put(DatabaseContract.FavoritesColumn.DESCRIPTION, movie.getOverview());
                cv.put(DatabaseContract.FavoritesColumn.POSTER, movie.getPoster());
                cv.put(DatabaseContract.FavoritesColumn.VOTE, movie.getVoteAverageRate());
                cv.put(DatabaseContract.FavoritesColumn.POPULARITY, movie.getPopularityRate());
                cv.put(DatabaseContract.FavoritesColumn.DATE, movie.getReleaseDate());

                mContentResolver.insert(CONTENT_URI, cv);
                getView().showMessage("Added to favorite");
                getView().showRemoveFromFavorite();
            } else {
                getView().showAddToFavorite();
            }
        } else if (cursor.moveToFirst()) {
            if (toggle) {
                mContentResolver.delete(detailUri, null, null);
                getView().showMessage("Removed to favorite");
                getView().showAddToFavorite();
            } else {
                getView().showRemoveFromFavorite();
            }
        }
    }

}
