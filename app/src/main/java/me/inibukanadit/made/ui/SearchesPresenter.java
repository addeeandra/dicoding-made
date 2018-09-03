package me.inibukanadit.made.ui;

import java.util.List;

import me.inibukanadit.made.db.helper.DictionaryHelper;
import me.inibukanadit.made.db.model.Dictionary;

public class SearchesPresenter {

    private SearchesView mView;
    private DictionaryHelper mDictionaryHelper;

    public SearchesPresenter(SearchesView mView, DictionaryHelper mDictionaryHelper) {
        this.mView = mView;
        this.mDictionaryHelper = mDictionaryHelper;
    }

    public List<Dictionary> searchWords(String search) {
        mDictionaryHelper = mDictionaryHelper.open();
        List<Dictionary> results = mDictionaryHelper.query(search);
        mDictionaryHelper.close();
        return results;
    }

}
