package com.core.app.ui.test;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.core.app.data.DataManager;
import com.core.app.data.db.SubTopic;
import com.core.app.data.db.WordByTopic;
import com.core.app.data.model.TestResultModel;
import com.core.app.ui.base.BasePresenter;
import com.core.app.utils.EnglisjViewUtils;
import com.core.ssvapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TestPresenter<V extends ITestView> extends BasePresenter<V> implements ITestPresenter<V> {
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
    public TestPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
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
    public void checkAnswer(CharSequence answer, Button button) {
        if (mCurrentTestIndex >= wordByTopics.size())
            return;

        WordByTopic currentWord = wordByTopics.get(mCurrentTestIndex);
        if (answer.equals(currentWord.getName())) {
            mCountCorrect++;
            button.setBackgroundResource(R.drawable.testbutton_correct);
            button.setTextColor(mContext.getResources().getColor(R.color.white));
            showCorrectWrong();
            playSound(mContext, R.raw.rightanswerclick, false);
            getMvpView().getCorrectView().startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));

        } else {
            mCountWrong++;
            button.setBackgroundResource(R.drawable.testbutton_wrong);
            button.setTextColor(mContext.getResources().getColor(R.color.white));
            showCorrectWrong();
            playSound(mContext, R.raw.wrongsound, false);
            getMvpView().getWrongView().startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.scale));
            changeButtonBg(currentWord.getName());
        }

        mCurrentTestIndex++;

        if (mCurrentTestIndex >= wordByTopics.size()) {
            showFullAds(mContext);
            //Save result test
            mResultTest.setTest1(mCountCorrect*100/wordByTopics.size());
            getDataManager().saveResultTest(mSubTopicId,gson.toJson(mResultTest));

            //TODO: Show result
            Handler handler = new Handler();
            handler.postDelayed(() -> getMvpView().showResultTest(mCountCorrect, mCountWrong), 1000);

        } else {
            Handler handler = new Handler();
            handler.postDelayed(this::showQA, 1000);
        }

    }

    private void changeButtonBg(CharSequence answer) {
        if (getMvpView().getAnswer1().getText().equals(answer)) {
            getMvpView().getAnswer1().setBackgroundResource(R.drawable.testbutton_correct);
            getMvpView().getAnswer1().setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (getMvpView().getAnswer2().getText().equals(answer)) {
            getMvpView().getAnswer2().setBackgroundResource(R.drawable.testbutton_correct);
            getMvpView().getAnswer2().setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (getMvpView().getAnswer3().getText().equals(answer)) {
            getMvpView().getAnswer3().setBackgroundResource(R.drawable.testbutton_correct);
            getMvpView().getAnswer3().setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (getMvpView().getAnswer4().getText().equals(answer)) {
            getMvpView().getAnswer4().setBackgroundResource(R.drawable.testbutton_correct);
            getMvpView().getAnswer4().setTextColor(mContext.getResources().getColor(R.color.white));
        }
    }

    private void showCorrectWrong() {
        getMvpView().showResult(": " + mCountCorrect, ": " + mCountWrong);
    }

    private void showQA() {
        WordByTopic currentWord = wordByTopics.get(mCurrentTestIndex);
        List<String> allAnswer = new ArrayList<>();
        for (int i = 0; i < wordByTopics.size(); i++) {
            if (mCurrentTestIndex == i)
                continue;
            allAnswer.add(wordByTopics.get(i).getName());
        }
        Collections.shuffle(allAnswer);
        String[] eng = (String[]) allAnswer.toArray(new String[allAnswer.size()]);
        String[] buttonTx = new String[]{currentWord.getName(), eng[0], eng[1], eng[2]};
        Collections.shuffle(Arrays.asList(buttonTx));

        getMvpView().showQA(EnglisjViewUtils.getDrawableIDFromName(mContext, currentWord.getImageResourceId()), buttonTx[0], buttonTx[1], buttonTx[2], buttonTx[3]);
        Handler handler = new Handler();
        handler.postDelayed(() -> playSound(mContext, EnglisjViewUtils.getRawIDFromName(mContext, currentWord.getSound()), false), 800);

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
