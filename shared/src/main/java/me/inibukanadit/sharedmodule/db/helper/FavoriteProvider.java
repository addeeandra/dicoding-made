package me.inibukanadit.sharedmodule.db.helper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.inibukanadit.sharedmodule.db.DatabaseContract;

import static me.inibukanadit.sharedmodule.db.DatabaseContract.AUTHORITY;
import static me.inibukanadit.sharedmodule.db.DatabaseContract.CONTENT_URI;

public class FavoriteProvider extends ContentProvider {

    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITES, FAVORITE);
        mUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVORITES + "/#", FAVORITE_ID);
    }

    private FavoriteHelper mHelper;

    @Override
    public boolean onCreate() {
        mHelper = new FavoriteHelper(getContext());
        mHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (mUriMatcher.match(uri)) {
            case FAVORITE:
                cursor = mHelper.queryProvider();
                break;
            case FAVORITE_ID:
                cursor = mHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;

        switch (mUriMatcher.match(uri)) {
            case FAVORITE:
                added = mHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (mUriMatcher.match(uri)) {
            case FAVORITE_ID:
                deleted = mHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated;
        switch (mUriMatcher.match(uri)) {
            case FAVORITE_ID:
                updated = mHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updated;

    }
}
