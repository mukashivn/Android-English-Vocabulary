package com.core.app.ui.home;

import android.content.Context;

import com.core.app.data.db.Topic;
import com.core.app.di.PerActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface IHomePresenter<V extends IHomeView> extends IPresenter<V> {
    void init(Context context);

    void loadAds();

    void openBottomSheet(Topic topic);

    void loadTopic();

    void handleShowSubTopic(int which);

    void reloadData();
}
