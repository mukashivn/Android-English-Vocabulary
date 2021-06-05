package com.core.app.ui.listen;

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
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ListenPresenter<V extends IListenView> extends BasePresenter<V> implements IListenPresenter<V> {
    private Context mContext;
    private int mSubTopicId;
    private String mMainTopicTitle;
    private int mCurrentTestIndex = 0;
    private int mCountCorrect = 0;
    private int mCountWrong = 0;
    private ArrayList<WordByTopic> wordByTopics;
    private int mCurrentSound;
    private TestResultModel mResultTest;
    private Gson gson;

    @Inject
    public ListenPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
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
    public void checkAnswer(boolean answer) {
        if (mCurrentTestIndex >= wordByTopics.size())
            return;

        WordByTopic currentWord = wordByTopics.get(mCurrentTestIndex);
        int soundCorrectId = EnglisjViewUtils.getRawIDFromName(mContext, currentWord.getSound());

        if ((answer && mCurrentSound == soundCorrectId)
                || (!answer && mCurrentSound != soundCorrectId)
                ) {
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
            //TODO: Show result
            //Save result test
            showFullAds(mContext);
            mResultTest.setListen1(mCountCorrect*100/wordByTopics.size());
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
        playSound(mContext, mCurrentSound, false);
    }

    private void showCorrectWrong() {
        getMvpView().showResult(": " + mCountCorrect, ": " + mCountWrong);
    }

    private void showQA() {
        WordByTopic currentWord = wordByTopics.get(mCurrentTestIndex);
        getMvpView().showQA(EnglisjViewUtils.getDrawableIDFromName(mContext, currentWord.getImageResourceId()));
        ArrayList<WordByTopic> temp = new ArrayList<>(wordByTopics);
        temp.remove(mCurrentTestIndex);
        Random r = new Random();
        int randomSoundId = r.nextInt(temp.size());

        String randomSoundRawName = temp.get(randomSoundId).getSound();
        int rawRandomId = EnglisjViewUtils.getRawIDFromName(mContext, randomSoundRawName);
        int correctRawId = EnglisjViewUtils.getRawIDFromName(mContext, currentWord.getSound());
        ArrayList<Integer> rawRandom = new ArrayList<>();
        rawRandom.add(rawRandomId);
        rawRandom.add(correctRawId);

        Collections.shuffle(rawRandom);
        randomSoundId = r.nextInt(2);
        this.mCurrentSound = rawRandom.get(randomSoundId);
        getMvpView().getCorrectAnswerView().setVisibility(View.INVISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(() -> playSound(mContext, mCurrentSound, false),800);

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
