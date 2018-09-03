package me.inibukanadit.made.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static me.inibukanadit.made.db.DatabaseContract.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbdictionary";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_INDONESIA =
            String.format("CREATE TABLE %s (" +
                            "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL" +
                            ")",
                    TABLE_DICT_INDONESIA,
                    DictIndonesiaColumns._ID,
                    DictIndonesiaColumns.WORD,
                    DictIndonesiaColumns.TRANSLATE
            );

    private static final String SQL_CREATE_TABLE_ENGLISH =
            String.format("CREATE TABLE %s (" +
                            "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL" +
                            ")",
                    TABLE_DICT_ENGLISH,
                    DictEnglishColumns._ID,
                    DictEnglishColumns.WORD,
                    DictEnglishColumns.TRANSLATE
            );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_INDONESIA);
        db.execSQL(SQL_CREATE_TABLE_ENGLISH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_DICT_ENGLISH);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_DICT_INDONESIA);
        onCreate(db);
    }
}
