package me.inibukanadit.made.data.db.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import me.inibukanadit.made.data.db.DatabaseContract;

import static me.inibukanadit.made.data.db.DatabaseContract.getColumnInt;
import static me.inibukanadit.made.data.db.DatabaseContract.FavoritesColumn;
import static me.inibukanadit.made.data.db.DatabaseContract.getColumnString;

public class Favorite implements Parcelable {

    private int id;
    private String title;
    private String description;
    private String date;

    public Favorite() {
    }

    public Favorite(Cursor cursor) {
        id = getColumnInt(cursor, FavoritesColumn._ID);
        title = getColumnString(cursor, FavoritesColumn.TITLE);
        description = getColumnString(cursor, FavoritesColumn.DESCRIPTION);
        date = getColumnString(cursor, FavoritesColumn.DATE);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    protected Favorite(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
