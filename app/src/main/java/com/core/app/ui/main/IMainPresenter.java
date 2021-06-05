package com.core.app.ui.main;

import android.content.Context;

import com.core.app.di.PerActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface IMainPresenter<V extends IMainView> extends IPresenter<V> {
    void init(Context activity);

    void changeLang(String langBra);

    void onRateComplete(boolean isExit, boolean isDontAgain, boolean isRate);

    void handleExitApp();

    void rateMe(boolean b);

    void handleShowFullAds();
}
