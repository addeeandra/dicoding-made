package me.inibukanadit.made.ui.base;

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V view);

    void onDetach();

}
