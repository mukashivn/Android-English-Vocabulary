package com.core.app.ui.test;

import android.content.Context;
import android.widget.Button;

import com.core.app.di.PerActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface ITestPresenter<V extends ITestView> extends IPresenter<V> {
    void init(Context context, String mainTopicTitle, int subTopicId);

    void loadData();

    void checkAnswer(CharSequence text, Button button);

    public void restest();
}
