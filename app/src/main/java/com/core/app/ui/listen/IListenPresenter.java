package com.core.app.ui.listen;

import android.content.Context;

import com.core.app.di.PerActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface IListenPresenter<V extends IListenView> extends IPresenter<V> {
    void init(Context context, String mainTopicTitle, int subTopicId);

    void loadData();

    void checkAnswer(boolean answer);

    void play();

    void restest();
}
