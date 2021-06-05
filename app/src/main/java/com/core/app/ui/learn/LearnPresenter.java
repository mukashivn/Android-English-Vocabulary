package com.core.app.ui.learn;

import android.content.Context;

import com.core.app.data.DataManager;
import com.core.app.data.db.SubTopic;
import com.core.app.data.db.WordByTopic;
import com.core.app.ui.base.BasePresenter;
import com.core.app.utils.AppConstants;
import com.core.app.utils.CommonUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LearnPresenter<V extends ILearnView> extends BasePresenter<V> implements ILearnPresenter<V> {
    private Context mContext;
    private int mSubTopicId;
    private String mMainTopicTitle;
    private int mCountNativeAds = 0;
    private int mTotalNativeAds;

    @Inject
    public LearnPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void init(Context context, String mainTopicTitle, int subTopicId) {
        this.mContext = context;
        this.mSubTopicId = subTopicId;
        this.mMainTopicTitle = mainTopicTitle;
    }

    @Override
    public void loadData() {
        if (mSubTopicId < 0)
            return;
        SubTopic subTopic = getDataManager().getSubTopicById(mSubTopicId);
        ArrayList<WordByTopic> wordByTopics = getDataManager().getWordsByTopic(subTopic.getId());
        if (wordByTopics.size() <= 0)
            return;
        mTotalNativeAds = wordByTopics.size() / (AppConstants.ADS_DELTA + 2);

        getMvpView().showTitle(mMainTopicTitle, subTopic.getName());
        getMvpView().showWords(CommonUtils.convertToWordWrapper(wordByTopics), getDataManager().getCurrentLang());
    }

    @Override
    public void loadAds() {
        mCountNativeAds++;
        if (mCountNativeAds > mTotalNativeAds) {
            return;
        }
        loadNativeAds(mContext);
    }

}
