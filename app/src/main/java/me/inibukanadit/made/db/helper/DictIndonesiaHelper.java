package me.inibukanadit.made.db.helper;

import android.content.Context;

import static me.inibukanadit.made.db.DatabaseContract.TABLE_DICT_INDONESIA;

public class DictIndonesiaHelper extends DictionaryHelper {

    public DictIndonesiaHelper(Context context) {
        super(context);
    }

    @Override
    String getTableName() {
        return TABLE_DICT_INDONESIA;
    }
}
