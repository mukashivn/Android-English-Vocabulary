package com.core.app.ui.base;

/**
 * Created by CuongCK on 2/13/17.
 */

public interface ISubView extends IView {
    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void attachParentIView(IView mvpView);
}
