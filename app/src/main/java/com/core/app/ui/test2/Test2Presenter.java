package com.core.app.ui.test2;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
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

public class Test2Presenter<V extends ITest2View> extends BasePresenter<V> implements ITest2Presenter<V> {
    private Context mContext;
    private int mSubTopicId;
    private String mMainTopicTitle;
    private int mCurrentTestIndex = 0;
    private int mCountCorrect = 0;
    private int mCountWrong = 0;
    private int mCurrentSound;
    private int mAnswer1;
    private int mAnsewer2;
    private TestResultModel mResultTest;
    private Gson gson;
    private ArrayList<WordByTopic> wordByTopics;

    @Inject
    public Test2Presenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
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
    public void checkAnswer(int i) {
        if(mCurrentTestIndex >= wordByTopics.size())
            return;

        //That mean choice ansewer 1
        if (i == 0) {
            if (this.mAnswer1 == this.mCurrentSound) {
                mCountCorrect++;
                getMvpView().getFrameAnswer1().setBackgroundResource(R.drawable.bg_rect_green);
                showCorrectWrong();
                playSound(mContext, R.raw.rightanswerclick, false);
                getMvpView().getCorrectView().startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));
            } else {
                mCountWrong++;
                getMvpView().getFrameAnswer2().setBackgroundResource(R.drawable.bg_rect_green);
                getMvpView().getFrameAnswer1().setBackgroundResource(R.drawable.bg_rect_red);
                showCorrectWrong();
                playSound(mContext, R.raw.wrongsound, false);
                getMvpView().getWrongView().startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));
            }
        } else {
            if (this.mAnsewer2 == this.mCurrentSound) {
                mCountCorrect++;
                getMvpView().getFrameAnswer2().setBackgroundResource(R.drawable.bg_rect_green);
                showCorrectWrong();
                playSound(mContext, R.raw.rightanswerclick, false);
                getMvpView().getCorrectView().startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));
            } else {
                mCountWrong++;
                getMvpView().getFrameAnswer1().setBackgroundResource(R.drawable.bg_rect_green);
                getMvpView().getFrameAnswer2().setBackgroundResource(R.drawable.bg_rect_red);
                showCorrectWrong();
                playSound(mContext, R.raw.wrongsound, false);
                getMvpView().getWrongView().startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));
            }
        }

        mCurrentTestIndex++;

        if (mCurrentTestIndex >= wordByTopics.size()) {
            //TODO: Show result
            showFullAds(mContext);
            //Save result test
            mResultTest.setTest2(mCountCorrect*100/wordByTopics.size());
            getDataManager().saveResultTest(mSubTopicId,gson.toJson(mResultTest));

            Handler handler = new Handler();
            handler.postDelayed(() -> getMvpView().showResultTest(mCountCorrect, mCountWrong), 1000);

        } else {
            Handler handler = new Handler();
            handler.postDelayed(this::showQA, 1000);
        }
    }

    private void showCorrectWrong() {
        getMvpView().showResult(": " + mCountCorrect, ": " + mCountWrong);
    }

    private void showQA() {
        WordByTopic currentWord = wordByTopics.get(mCurrentTestIndex);
        // getMvpView().showQA(EnglisjViewUtils.getDrawableIDFromName(mContext, currentWord.getImageResourceId()));
        ArrayList<WordByTopic> temp = new ArrayList<>(wordByTopics);
        temp.remove(mCurrentTestIndex);
        Random r = new Random();
        int randomSoundId = r.nextInt(temp.size());

        String randomSoundRawName = temp.get(randomSoundId).getImageResourceId();
        int rawRandomId = EnglisjViewUtils.getDrawableIDFromName(mContext, randomSoundRawName);
        int correctRawId = EnglisjViewUtils.getDrawableIDFromName(mContext, currentWord.getImageResourceId());
        ArrayList<Integer> rawRandom = new ArrayList<>();
        rawRandom.add(rawRandomId);
        rawRandom.add(correctRawId);

        Collections.shuffle(rawRandom);
        this.mCurrentSound = correctRawId;
        this.mAnswer1 = rawRandom.get(0);
        this.mAnsewer2 = rawRandom.get(1);

        getMvpView().showQA(currentWord.getName(), this.mAnswer1, this.mAnsewer2);
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
