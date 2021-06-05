package com.core.app.data;

import android.content.Context;

import com.core.app.data.db.SubTopic;
import com.core.app.data.db.Topic;
import com.core.app.data.db.WordByTopic;
import com.core.app.data.model.SubTopicModel;
import com.core.app.data.prefs.PreferencesHelper;
import com.core.app.data.realm.RealmHelper;
import com.core.app.data.network.ApiHelper;
import com.core.app.di.ApplicationContext;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;

/**
 * Created by CuongCK on 2/13/17.
 */
@Singleton
public class AppDataManager implements DataManager {
    private static final String TAG = "AppDataManager";


    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;
    private final RealmHelper mRealmHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context mContext, PreferencesHelper mPreferencesHelper, ApiHelper mApiHelper, RealmHelper realmHelper) {
        this.mContext = mContext;
        this.mPreferencesHelper = mPreferencesHelper;
        this.mApiHelper = mApiHelper;
        this.mRealmHelper = realmHelper;
    }

    @Override
    public ArrayList<Topic> getListTopPic() {
        return mRealmHelper.getListTopPic();
    }

    @Override
    public ArrayList<SubTopic> getSubTopic(int topicID) {
        return mRealmHelper.getSubTopic(topicID);
    }

    @Override
    public ArrayList<SubTopicModel> getSubTopicModel(int topicID) {
        return mRealmHelper.getSubTopicModel(topicID);
    }

    @Override
    public ArrayList<WordByTopic> getWordsByTopic(int subTopic) {
        return mRealmHelper.getWordsByTopic(subTopic);
    }

    @Override
    public Topic getTopicById(int id) {
        return mRealmHelper.getTopicById(id);
    }

    @Override
    public SubTopic getSubTopicById(int id) {
        return mRealmHelper.getSubTopicById(id);
    }

    @Override
    public WordByTopic getWorldById(int id) {
        return mRealmHelper.getWorldById(id);
    }

    @Override
    public void saveResultTest(int subTopicID, String result) {
        mRealmHelper.saveResultTest(subTopicID, result);
    }

    @Override
    public Realm getRealm() {
        return mRealmHelper.getRealm();
    }

    @Override
    public void saveCurrentLang(String currentLang) {
        mPreferencesHelper.saveCurrentLang(currentLang);
    }

    @Override
    public String getCurrentLang() {
        return mPreferencesHelper.getCurrentLang();
    }

    @Override
    public void setDonAgain() {
        mPreferencesHelper.setDonAgain();
    }

    @Override
    public boolean getDontAgain() {
        return mPreferencesHelper.getDontAgain();
    }

    @Override
    public boolean isAllowShowRate() {
        return mPreferencesHelper.isAllowShowRate();
    }

    @Override
    public void countOpenApp() {
        mPreferencesHelper.countOpenApp();
    }

    @Override
    public void resetOpenApp() {
        mPreferencesHelper.resetOpenApp();
    }

    @Override
    public void savePurchase(String key, boolean value) {
        mPreferencesHelper.savePurchase(key, value);
    }

    @Override
    public boolean isPurChase(String key) {
        return mPreferencesHelper.isPurChase(key);
    }
}
