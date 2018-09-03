package me.inibukanadit.made.db.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dictionary implements Parcelable {

    private String word;

    private String translate;

    public Dictionary() {
    }

    public Dictionary(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }

    protected Dictionary(Parcel in) {
        word = in.readString();
        translate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(translate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dictionary> CREATOR = new Creator<Dictionary>() {
        @Override
        public Dictionary createFromParcel(Parcel in) {
            return new Dictionary(in);
        }

        @Override
        public Dictionary[] newArray(int size) {
            return new Dictionary[size];
        }
    };

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}
