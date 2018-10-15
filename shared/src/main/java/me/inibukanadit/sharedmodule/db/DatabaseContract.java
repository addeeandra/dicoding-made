package me.inibukanadit.sharedmodule.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_FAVORITES = "favorites";

    public static final class FavoritesColumn implements BaseColumns {
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
    }

    public static final String AUTHORITY = "me.inibukanadit.made.moviesapp";

    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITES)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

}
