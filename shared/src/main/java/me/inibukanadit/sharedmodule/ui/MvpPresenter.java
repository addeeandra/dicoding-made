package me.inibukanadit.sharedmodule.ui;

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V view);

    void onDetach();

}
