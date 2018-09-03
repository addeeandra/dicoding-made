package me.inibukanadit.made.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import me.inibukanadit.made.db.DatabaseHelper;
import me.inibukanadit.made.db.model.Dictionary;

import static me.inibukanadit.made.db.DatabaseContract.DictColumns;

public abstract class DictionaryHelper {

    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    DictionaryHelper(Context context) {
        mContext = context;
    }

    public DictionaryHelper open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void beginTransaction() {
        mDatabase.beginTransaction();
    }

    public void setTransactionSuccessful() {
        mDatabase.setTransactionSuccessful();
    }

    public void endTransaction() {
        mDatabase.endTransaction();
    }

    public void close() {
        mDatabaseHelper.close();
    }

    public List<Dictionary> query(String search) {
        Cursor cursor = mDatabase.query(getTableName(), null, DictColumns.WORD + " LIKE '%" + search + "%'", null, null, null, DictColumns.WORD + " ASC", "20");
        cursor.moveToFirst();

        ArrayList<Dictionary> arrayList = new ArrayList<>();
        Dictionary dict;
        if (cursor.getCount() > 0) {
            do {
                String word = cursor.getString(cursor.getColumnIndexOrThrow(DictColumns.WORD));
                String translate = cursor.getString(cursor.getColumnIndexOrThrow(DictColumns.TRANSLATE));
                dict = new Dictionary(word, translate);

                arrayList.add(dict);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();

        return arrayList;
    }

    public void insertTransaction(Dictionary dictionary) {
        String sql = "INSERT INTO " + getTableName() + " (" + DictColumns.WORD + ", " + DictColumns.TRANSLATE + ") VALUES (?, ?)";
        SQLiteStatement stmt = mDatabase.compileStatement(sql);
        stmt.bindString(1, dictionary.getWord());
        stmt.bindString(2, dictionary.getTranslate());
        stmt.execute();
        stmt.clearBindings();
    }

    public long insert(Dictionary dictionary) {
        ContentValues cv = new ContentValues();
        cv.put(DictColumns.WORD, dictionary.getWord());
        cv.put(DictColumns.TRANSLATE, dictionary.getTranslate());
        return mDatabase.insert(getTableName(), null, cv);
    }

    abstract String getTableName();

}
