package com.core.app.data.realm;

import android.content.Context;

import com.core.app.data.db.SubTopic;
import com.core.app.data.db.Topic;
import com.core.app.data.db.WordByTopic;
import com.core.app.data.model.SubTopicModel;
import com.core.app.di.ApplicationContext;
import com.core.app.utils.LogUtils;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Package: com.core.ssvapp.data.realm
 * Created by: CuongCK
 * Date: 6/1/17
 */
@Singleton
public class AppRealmHelper implements RealmHelper {
    private final Realm realm;

    @Inject
    public AppRealmHelper(@ApplicationContext Context application) {
        Realm.init(application);
        final RealmConfiguration configuration =
                new RealmConfiguration
                        .Builder()
                        .assetFile("database.realm")
                        .schemaVersion(1)
                        .build();
        Realm.setDefaultConfiguration(configuration);
        realm = Realm.getInstance(configuration);

        //TODO: If want to miragte. Do like that:
        /*
            final RealmConfiguration configuration =
                new RealmConfiguration
                .Builder()
                .name("imosys.realm")
                .schemaVersion(2)
                .migration(new RealmMigrations())
                .build();
         */
    }


    @Override
    public ArrayList<Topic> getListTopPic() {
        try {
            RealmResults<Topic> results = realm.where(Topic.class).findAll();
            if (results != null) {
                return new ArrayList<>(results);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<SubTopic> getSubTopic(int topicID) {
        try {
            RealmResults<SubTopic> results = realm.where(SubTopic.class).equalTo("topicId", topicID).findAll().sort("name", Sort.ASCENDING);
            if (results != null) {
                return new ArrayList<>(results);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<SubTopicModel> getSubTopicModel(int topicID) {
        try {
            ArrayList<SubTopicModel> newList = new ArrayList<>();
            RealmResults<SubTopic> results = realm.where(SubTopic.class).equalTo("topicId", topicID).findAll().sort("name", Sort.ASCENDING);
            if (results != null) {
                for (SubTopic item : results) {
                    newList.add(item.map2Model());
                }
                return newList;
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<WordByTopic> getWordsByTopic(int subTopic) {
        try {
            RealmResults<WordByTopic> results = realm.where(WordByTopic.class).equalTo("subTopicId", subTopic).findAll().sort("name", Sort.ASCENDING);
            ;
            if (results != null) {
                return new ArrayList<>(results);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return null;
    }

    @Override
    public Topic getTopicById(int id) {
        try {
            return realm.where(Topic.class).equalTo("id", id).findFirst();
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return null;
    }

    @Override
    public SubTopic getSubTopicById(int id) {
        try {
            return realm.where(SubTopic.class).equalTo("id", id).findFirst();
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return null;
    }

    @Override
    public WordByTopic getWorldById(int id) {
        try {
            return realm.where(WordByTopic.class).equalTo("id", id).findFirst();
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return null;
    }

    @Override
    public void saveResultTest(int subTopicID, String result) {
        try {
            realm.executeTransaction(realm1 -> {
                SubTopic subTopic = realm1.where(SubTopic.class).equalTo("id", subTopicID).findFirst();
                if (subTopic != null) {
                    subTopic.setNone(result);
                    realm1.copyToRealmOrUpdate(subTopic);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Realm getRealm() {
        return realm;
    }
}
