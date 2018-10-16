package me.inibukanadit.sharedmodule.ui;

interface MvpPresenter<V extends MvpView> {

    void onAttach(V view);

    void onDetach();

}
