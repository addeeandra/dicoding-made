package me.inibukanadit.made.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_DICT_INDONESIA = "dict_indonesia";
    public static String TABLE_DICT_ENGLISH = "dict_english";

    public interface DictColumns extends BaseColumns {

        String WORD = "word";

        String TRANSLATE = "translate";

    }

    static final class DictIndonesiaColumns implements DictColumns { }

    static final class DictEnglishColumns implements DictColumns { }

}
