package me.inibukanadit.made.ui.base;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment implements MvpSubView {

    private MvpView mParentView;

    @Override
    public void onError(int message) {
        mParentView.onError(message);
    }

    @Override
    public void onError(String message) {
        mParentView.onError(message);
    }

    @Override
    public void showMessage(int message) {
        mParentView.showMessage(message);
    }

    @Override
    public void showMessage(String message) {
        mParentView.showMessage(message);
    }

    @Override
    public boolean isNetworkConnected() {
        return mParentView.isNetworkConnected();
    }

    @Override
    public MvpView getParentView() {
        return mParentView;
    }

    @Override
    public void setParentView(MvpView parentView) {
        mParentView = parentView;
    }

}
