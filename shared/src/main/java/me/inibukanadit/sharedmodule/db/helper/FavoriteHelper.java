package me.inibukanadit.sharedmodule.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import me.inibukanadit.sharedmodule.db.DatabaseContract;
import me.inibukanadit.sharedmodule.db.DatabaseHelper;

import static android.provider.BaseColumns._ID;

public class FavoriteHelper {

    private static final String DATABASE_TABLE = DatabaseContract.TABLE_FAVORITES;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;

    private SQLiteDatabase mDatabase;

    public FavoriteHelper(Context context) {
        mContext = context;
    }

    FavoriteHelper open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public Cursor queryByIdProvider(String id) {
        return mDatabase.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null);
    }

    public Cursor queryProvider() {
        return mDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC"
        );
    }

    public long insertProvider(ContentValues cv) {
        return mDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public int updateProvider(String id, ContentValues cv) {
        return mDatabase.update(DATABASE_TABLE, cv, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return mDatabase.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
