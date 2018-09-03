package me.inibukanadit.made.db.helper;

import android.content.Context;

import static me.inibukanadit.made.db.DatabaseContract.TABLE_DICT_ENGLISH;

public class DictEnglishHelper extends DictionaryHelper {

    public DictEnglishHelper(Context context) {
        super(context);
    }

    @Override
    String getTableName() {
        return TABLE_DICT_ENGLISH;
    }
}
