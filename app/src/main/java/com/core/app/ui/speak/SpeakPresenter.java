package com.core.app.ui.speak;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.core.app.data.DataManager;
import com.core.app.data.db.SubTopic;
import com.core.app.data.db.WordByTopic;
import com.core.app.data.model.TestResultModel;
import com.core.app.ui.base.BasePresenter;
import com.core.app.utils.EnglisjViewUtils;
import com.core.ssvapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SpeakPresenter<V extends ISpeakView> extends BasePresenter<V> implements ISpeakPresenter<V> {
    private Context mContext;
    private int mSubTopicId;
    private String mMainTopicTitle;
    private int mCurrentTestIndex = 0;
    private int mCountCorrect = 0;
    private int mCountWrong = 0;
    private ArrayList<WordByTopic> wordByTopics;
    private TestResultModel mResultTest;
    private Gson gson;

    @Inject
    public SpeakPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
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
        gson = new Gson();
        SubTopic subTopic = getDataManager().getSubTopicById(mSubTopicId);

        String testRes = subTopic.getNone();
        if (testRes != null && !TextUtils.isEmpty(testRes)) {

            mResultTest = gson.fromJson(testRes, TestResultModel.class);
        } else {
            mResultTest = new TestResultModel(0, 0, 0, 0, 0, 0);
        }

        wordByTopics = getDataManager().getWordsByTopic(subTopic.getId());

        if (wordByTopics.size() <= 0)
            return;

        Collections.shuffle(wordByTopics);
        getMvpView().showTitle(mMainTopicTitle, subTopic.getName());

        //Show default
        showCorrectWrong();
        //Show QA
        showQA();
    }

    @Override
    public void checkAnswer(String text) {
        if (mCurrentTestIndex >= wordByTopics.size())
            return;
        checkAns(text);
    }

    private void checkAns(String text) {
        getMvpView().getYourVoice().setVisibility(View.VISIBLE);
        getMvpView().getYourVoice().setText(mContext.getString(R.string.your_voice) + " " + text);
        WordByTopic current = wordByTopics.get(mCurrentTestIndex);
        if(current.getName().equalsIgnoreCase(text)){
            mCountCorrect++;
            showCorrectWrong();
            playSound(mContext, R.raw.rightanswerclick, false);
            getMvpView().getCorrectView().startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));

        }else {
            mCountWrong++;
            showCorrectWrong();
            playSound(mContext, R.raw.wrongsound, false);
            getMvpView().getWrongView().startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));
        }

        mCurrentTestIndex++;

        if (mCurrentTestIndex >= wordByTopics.size()) {
            //TODO: Show result
            //Save result test
            showFullAds(mContext);
            mResultTest.setSpeak(mCountCorrect*100/wordByTopics.size());
            getDataManager().saveResultTest(mSubTopicId,gson.toJson(mResultTest));
            Handler handler = new Handler();
            handler.postDelayed(() -> getMvpView().showResultTest(mCountCorrect, mCountWrong), 1000);

        } else {
            //Next question
            Handler handler = new Handler();
            handler.postDelayed(this::showQA, 1000);
        }
    }

    private void showCorrectWrong() {
        getMvpView().showResult(": " + mCountCorrect, ": " + mCountWrong);
    }

    private void showQA(){
        getMvpView().getYourVoice().setVisibility(View.GONE);
        WordByTopic current = wordByTopics.get(mCurrentTestIndex);
        getMvpView().showQA(EnglisjViewUtils.getDrawableIDFromName(mContext,current.getImageResourceId()));
    }

    public void restest() {
        mCountCorrect = 0;
        mCountWrong = 0;
        mCurrentTestIndex = 0;

        Collections.shuffle(wordByTopics);

        //Show default
        showCorrectWrong();
        //Show QA
        showQA();
    }
}
