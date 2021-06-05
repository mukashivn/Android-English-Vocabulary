package com.core.app.ui.writing;

import android.content.Context;

import com.core.app.di.PerActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface IWritingPresenter<V extends IWritingView> extends IPresenter<V> {
    void init(Context context, String mainTopicTitle, int subTopicId);

    void loadData();

    void checkAnswer(String answer);

    void play();

    void restest();
}
