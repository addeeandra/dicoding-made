package me.inibukanadit.sharedmodule.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbmoviesapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITES = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s DOUBLE DEFAULT 0," +
                    "%s DOUBLE DEFAULT 0," +
                    "%s TEXT NOT NULL" +
                    ")",
            DatabaseContract.TABLE_FAVORITES,
            DatabaseContract.FavoritesColumn._ID,
            DatabaseContract.FavoritesColumn.TITLE,
            DatabaseContract.FavoritesColumn.DESCRIPTION,
            DatabaseContract.FavoritesColumn.VOTE,
            DatabaseContract.FavoritesColumn.POPULARITY,
            DatabaseContract.FavoritesColumn.DATE
    );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAVORITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITES);
        onCreate(sqLiteDatabase);
    }

}
