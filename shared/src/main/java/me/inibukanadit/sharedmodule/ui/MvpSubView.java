package me.inibukanadit.sharedmodule.ui;

import android.support.annotation.StringRes;

interface MvpSubView {

    void onError(@StringRes int message);

    void onError(String message);

    void showMessage(@StringRes int message);

    void showMessage(String message);

    boolean isNetworkConnected();

    MvpView getParentView();

    void setParentView(MvpView parentView);

}
