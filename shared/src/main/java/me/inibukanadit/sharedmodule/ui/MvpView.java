package me.inibukanadit.sharedmodule.ui;

import android.support.annotation.StringRes;

public interface MvpView {

    void onError(@StringRes int message);

    void onError(String message);

    void showMessage(@StringRes int message);

    void showMessage(String message);

    boolean isNetworkConnected();

}
