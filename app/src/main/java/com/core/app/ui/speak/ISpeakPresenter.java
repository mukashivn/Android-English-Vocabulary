package com.core.app.ui.speak;

import android.content.Context;

import com.core.app.di.PerActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface ISpeakPresenter<V extends ISpeakView> extends IPresenter<V> {
    void init(Context context, String mainTopicTitle, int subTopicId);

    void loadData();

    void checkAnswer(String text);

    void restest();
}
