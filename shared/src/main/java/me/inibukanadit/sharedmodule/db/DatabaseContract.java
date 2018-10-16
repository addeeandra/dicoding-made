package me.inibukanadit.sharedmodule.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String TABLE_FAVORITES = "favorites";

    public static final class FavoritesColumn implements BaseColumns {
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String VOTE = "vote";
        public static final String POPULARITY = "popularity";
        public static final String DATE = "date";
    }

    public static final String AUTHORITY = "me.inibukanadit.made.moviesapp";

    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITES)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

}
