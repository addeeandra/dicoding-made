package me.inibukanadit.made.ui.main;

import android.support.v4.app.Fragment;

import java.util.List;

import me.inibukanadit.made.ui.base.MvpView;

public interface MainView extends MvpView {

    List<Fragment> getPages();

    List<String> getTitles();

}
