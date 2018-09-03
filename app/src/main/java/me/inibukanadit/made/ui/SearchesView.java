package me.inibukanadit.made.ui;

import java.util.List;

import me.inibukanadit.made.db.model.Dictionary;

public interface SearchesView {

    void showWords(List<Dictionary> words);

    void showPlaceholder();

    void showMessage(String message);

}
