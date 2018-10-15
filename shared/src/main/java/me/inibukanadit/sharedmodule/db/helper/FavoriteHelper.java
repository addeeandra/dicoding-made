package me.inibukanadit.sharedmodule.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import me.inibukanadit.sharedmodule.db.DatabaseContract;
import me.inibukanadit.sharedmodule.db.DatabaseHelper;
import me.inibukanadit.sharedmodule.db.entity.Favorite;

import static android.provider.BaseColumns._ID;
import static me.inibukanadit.sharedmodule.db.DatabaseContract.FavoritesColumn.DATE;
import static me.inibukanadit.sharedmodule.db.DatabaseContract.FavoritesColumn.DESCRIPTION;
import static me.inibukanadit.sharedmodule.db.DatabaseContract.FavoritesColumn.TITLE;

public class FavoriteHelper {

    private static final String DATABASE_TABLE = DatabaseContract.TABLE_FAVORITES;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;

    private SQLiteDatabase mDatabase;

    public FavoriteHelper(Context context) {
        mContext = context;
    }

    public FavoriteHelper open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public ArrayList<Favorite> query() {
        ArrayList<Favorite> arrayList = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
        cursor.moveToFirst();

        Favorite favorite;
        if (cursor.getCount() > 0) {
            do {
                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                favorite.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                favorite.setDate(cursor.getString(cursor.getColumnIndex(DATE)));

                arrayList.add(favorite);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return arrayList;
    }

    public long insert(Favorite favorite) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, favorite.getTitle());
        cv.put(DESCRIPTION, favorite.getDescription());
        cv.put(DATE, favorite.getDate());
        return mDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public int update(Favorite favorite) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, favorite.getTitle());
        cv.put(DESCRIPTION, favorite.getDescription());
        cv.put(DATE, favorite.getDate());
        return mDatabase.update(DATABASE_TABLE, cv, _ID + " = '" + favorite.getId() + "'", null);
    }

    public int delete(int id) {
        return mDatabase.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
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
