package com.core.app.ui.flash;

import android.content.Context;

import com.core.app.di.PerActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface IFlashPresenter<V extends IFlashView> extends IPresenter<V> {
    void init(Context context, String mainTopicTitle, int subTopicId);

    void loadData();

    void previous();

    void flip();

    void flip2();

    void next();

    void play();
}
