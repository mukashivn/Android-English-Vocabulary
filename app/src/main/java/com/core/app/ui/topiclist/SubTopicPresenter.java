package com.core.app.ui.topiclist;

import android.content.Context;

import com.core.app.data.DataManager;
import com.core.app.data.db.Topic;
import com.core.app.data.model.SubTopicModel;
import com.core.app.ui.base.BasePresenter;
import com.core.app.ui.flash.FlashAcivity;
import com.core.app.ui.learn.LearnActivity;
import com.core.app.ui.listen.ListenActivity;
import com.core.app.ui.listen2.Listen2Activity;
import com.core.app.ui.speak.SpeakActivity;
import com.core.app.ui.test.TestActivity;
import com.core.app.ui.test2.Test2Activity;
import com.core.app.ui.writing.WritingActivity;
import com.core.app.utils.AppConstants;
import com.core.app.utils.CommonUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.RealmChangeListener;

public class SubTopicPresenter<V extends ISubTopicView> extends BasePresenter<V> implements ISubTopicPresenter<V>, RealmChangeListener {
    private Context mContext;
    private int mTopicId;
    private int mCourseType;

    @Inject
    public SubTopicPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        getDataManager().getRealm().addChangeListener(this);
    }

    @Override
    public void init(Context context, int topicId, int courseType) {
        this.mContext = context;
        this.mTopicId = topicId;
        this.mCourseType = courseType;
    }

    @Override
    public void loadSubTopic() {
        if (mTopicId < 0)
            return;

        String lang = getDataManager().getCurrentLang();
        Topic topic = getDataManager().getTopicById(mTopicId);

        getMvpView().showTitle(topic.getName(), mContext.getString(CommonUtils.getCourseName(mCourseType)));
        getMvpView().showSubTopicList(getDataManager().getSubTopicModel(mTopicId), lang, mCourseType);
    }

    @Override
    public void handleNextScreen(SubTopicModel subTopic) {
        switch (mCourseType){
            case AppConstants.COURSE_LEARN:
                getMvpView().showSubTopicDetail(getDataManager().getTopicById(mTopicId).getName(),subTopic.getId(), LearnActivity.class);
                break;
            case AppConstants.COURSE_FLASH:
                getMvpView().showSubTopicDetail(getDataManager().getTopicById(mTopicId).getName(),subTopic.getId(), FlashAcivity.class);
                break;
            case AppConstants.COURSE_TEST:
                getMvpView().showSubTopicDetail(getDataManager().getTopicById(mTopicId).getName(),subTopic.getId(), TestActivity.class);
                break;
            case AppConstants.COURSE_TEST_2:
                getMvpView().showSubTopicDetail(getDataManager().getTopicById(mTopicId).getName(),subTopic.getId(), Test2Activity.class);
                break;
            case AppConstants.COURSE_WRITING:
                getMvpView().showSubTopicDetail(getDataManager().getTopicById(mTopicId).getName(),subTopic.getId(), WritingActivity.class);
                break;
            case AppConstants.COURSE_LISTENING:
                getMvpView().showSubTopicDetail(getDataManager().getTopicById(mTopicId).getName(),subTopic.getId(), ListenActivity.class);
                break;
            case AppConstants.COURSE_LISTENING_2:
                getMvpView().showSubTopicDetail(getDataManager().getTopicById(mTopicId).getName(),subTopic.getId(), Listen2Activity .class);
                break;
            case AppConstants.COURSE_SPEAKING:
                getMvpView().showSubTopicDetail(getDataManager().getTopicById(mTopicId).getName(),subTopic.getId(), SpeakActivity.class);
                break;
        }
    }

    @Override
    public void onChange(Object o) {
        loadSubTopic();
    }

    @Override
    public void onDetach() {
        getDataManager().getRealm().removeChangeListener(this);
        super.onDetach();
    }
}
