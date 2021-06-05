package com.core.app.ui.topiclist;

import android.content.Context;

import com.core.app.data.model.SubTopicModel;
import com.core.app.di.PerActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface ISubTopicPresenter<V extends ISubTopicView> extends IPresenter<V>{
    void init(Context context, int topicId, int courseType);

    void loadSubTopic();

    void handleNextScreen(SubTopicModel subTopic);
}
