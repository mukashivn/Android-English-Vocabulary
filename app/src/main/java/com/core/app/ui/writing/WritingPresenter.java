package com.core.app.ui.writing;

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

public class WritingPresenter<V extends IWritingView> extends BasePresenter<V> implements IWritingPresenter<V> {
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
    public WritingPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
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
        if(wordByTopics.size() <= 0)
            return;
        Collections.shuffle(wordByTopics);
        getMvpView().showTitle(mMainTopicTitle, subTopic.getName());

        //Show default
        showCorrectWrong();
        //Show QA
        showQA();
    }

    @Override
    public void checkAnswer(String answer) {
        if (mCurrentTestIndex >= wordByTopics.size())
            return;
        if (TextUtils.isEmpty(answer))
            return;
        WordByTopic currentWord = wordByTopics.get(mCurrentTestIndex);
        if (answer.equalsIgnoreCase(currentWord.getName())) {
            mCountCorrect++;
            showCorrectWrong();
            playSound(mContext, R.raw.rightanswerclick, false);
            getMvpView().getCorrectView().startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));
        } else {
            mCountWrong++;
            showCorrectWrong();
            playSound(mContext, R.raw.wrongsound, false);
            getMvpView().getWrongView().startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));
        }

        getMvpView().getCorrectAnswerView().setText(mContext.getString(R.string.correct_answer) + " " + currentWord.getName());
        getMvpView().getCorrectAnswerView().setVisibility(View.VISIBLE);
        mCurrentTestIndex++;

        if (mCurrentTestIndex >= wordByTopics.size()) {
            showFullAds(mContext);
            //TODO: Show result
            //Save result test
            mResultTest.setWriting(mCountCorrect*100/wordByTopics.size());
            getDataManager().saveResultTest(mSubTopicId,gson.toJson(mResultTest));
            Handler handler = new Handler();
            handler.postDelayed(() -> getMvpView().showResultTest(mCountCorrect, mCountWrong), 1500);

        } else {
            Handler handler = new Handler();
            handler.postDelayed(this::showQA, 2000);
        }
    }

    @Override
    public void play() {
        WordByTopic currentWord = wordByTopics.get(mCurrentTestIndex);
        playSound(mContext, EnglisjViewUtils.getRawIDFromName(mContext, currentWord.getSound()), false);
    }

    @Override
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

    private void showCorrectWrong() {
        getMvpView().showResult(": " + mCountCorrect, ": " + mCountWrong);
    }

    private void showQA() {
        WordByTopic currentWord = wordByTopics.get(mCurrentTestIndex);
        getMvpView().getEdtAnswer().setText("");
        getMvpView().getCorrectAnswerView().setVisibility(View.INVISIBLE);
        getMvpView().showQA(EnglisjViewUtils.getDrawableIDFromName(mContext, currentWord.getImageResourceId()));
        Handler handler = new Handler();
        handler.postDelayed(() -> playSound(mContext, EnglisjViewUtils.getRawIDFromName(mContext, currentWord.getSound()), false),800);

    }
}
