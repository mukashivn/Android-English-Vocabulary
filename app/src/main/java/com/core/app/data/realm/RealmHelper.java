package com.core.app.data.realm;

import com.core.app.data.db.SubTopic;
import com.core.app.data.db.Topic;
import com.core.app.data.db.WordByTopic;
import com.core.app.data.model.SubTopicModel;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Package: com.core.ssvapp.data.realm
 * Created by: CuongCK
 * Date: 6/1/17
 */

public interface RealmHelper {
    ArrayList<Topic> getListTopPic();

    ArrayList<SubTopic> getSubTopic(int topicID);

    ArrayList<SubTopicModel> getSubTopicModel(int topicID);

    ArrayList<WordByTopic> getWordsByTopic(int subTopic);

    Topic getTopicById(int id);

    SubTopic getSubTopicById(int id);

    WordByTopic getWorldById(int id);

    void saveResultTest(int subTopicID, String result);

    Realm getRealm();
}
