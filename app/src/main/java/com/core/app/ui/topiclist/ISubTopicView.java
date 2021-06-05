package com.core.app.ui.topiclist;

import com.core.app.data.model.SubTopicModel;
import com.core.app.ui.base.IView;

import java.util.ArrayList;

public interface ISubTopicView extends IView {
    void showTitle(String topicName, String lang);

    void showSubTopicList(ArrayList<SubTopicModel> subTopics, String lang, int courseType);

    void showSubTopicDetail(String topicTitle, int subId, Class clazz);

}
