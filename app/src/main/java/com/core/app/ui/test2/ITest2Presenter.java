package com.core.app.ui.test2;

import android.content.Context;

import com.core.app.di.PerActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface ITest2Presenter<V extends ITest2View> extends IPresenter<V> {
    void init(Context context, String mainTopicTitle, int subTopicId);

    void loadData();

    void checkAnswer(int i);

    void restest();
}
