package com.core.app.ui.flash;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import com.core.app.data.DataManager;
import com.core.app.data.db.SubTopic;
import com.core.app.data.db.WordByTopic;
import com.core.app.ui.animation.FlipAnimation;
import com.core.app.ui.animation.FlipAnimation2;
import com.core.app.ui.base.BasePresenter;
import com.core.app.utils.CommonUtils;
import com.core.app.utils.EnglisjViewUtils;
import com.core.ssvapp.R;

import java.util.ArrayList;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class FlashPresenter<V extends IFlashView> extends BasePresenter<V> implements IFlashPresenter<V> {
    private Context mContext;
    private int mSubTopicId;
    private String mMainTopicTitle;
    ArrayList<WordByTopic> wordByTopics;
    private int mCurrentWordIndex = 0;

    @Inject
    public FlashPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
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
        if(mSubTopicId < 0)
            return;
        SubTopic subTopic = getDataManager().getSubTopicById(mSubTopicId);
        wordByTopics = getDataManager().getWordsByTopic(subTopic.getId());
        if(wordByTopics.size() <= 0)
            return;
        getMvpView().showTitle(mMainTopicTitle, subTopic.getName());
        getMvpView().showCardInfo(wordByTopics.get(mCurrentWordIndex), CommonUtils.getWordLang(getDataManager().getCurrentLang(), wordByTopics.get(mCurrentWordIndex)));
        getMvpView().showPageInfo((mCurrentWordIndex + 1) + "/" + (wordByTopics.size()));
    }

    @Override
    public void previous() {
        mCurrentWordIndex--;
        if(mCurrentWordIndex < 0) {
            mCurrentWordIndex = 0;
            return;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.flash_prev_1);
        animation.setInterpolator(new AccelerateInterpolator());
        getMvpView().getRootFlash().startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            getMvpView().getCardBack().setVisibility(View.INVISIBLE);
            getMvpView().getCardFront().setVisibility(View.VISIBLE);
            showCurrentCard();
            Animation animation1 = AnimationUtils.loadAnimation(mContext, R.anim.flash_prev_2);
            animation1.setInterpolator(new DecelerateInterpolator());
            getMvpView().getRootFlash().startAnimation(animation1);

        },200);
    }

    @Override
    public void flip() {
        FlipAnimation flipAnimation = new FlipAnimation(getMvpView().getCardFront(), getMvpView().getCardBack());
        if (getMvpView().getCardFront().getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        getMvpView().getRootFlash().startAnimation(flipAnimation);
    }

    @Override
    public void flip2() {
        FlipAnimation2 flipAnimation = new FlipAnimation2(getMvpView().getCardFront(), getMvpView().getCardBack());
        if (getMvpView().getCardFront().getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        getMvpView().getRootFlash().startAnimation(flipAnimation);
    }

    @Override
    public void next() {
        mCurrentWordIndex++;
        if(mCurrentWordIndex >= wordByTopics.size()) {
            mCurrentWordIndex = wordByTopics.size() - 1;
            return;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.flash_next_1);
        animation.setInterpolator(new AccelerateInterpolator());
        getMvpView().getRootFlash().startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            getMvpView().getCardBack().setVisibility(View.INVISIBLE);
            getMvpView().getCardFront().setVisibility(View.VISIBLE);
            showCurrentCard();
            Animation animation1 = AnimationUtils.loadAnimation(mContext, R.anim.flash_next_2);
            animation1.setInterpolator(new DecelerateInterpolator());
            getMvpView().getRootFlash().startAnimation(animation1);

        },200);
    }

    @Override
    public void play() {
        playSound(mContext, EnglisjViewUtils.getRawIDFromName(mContext,wordByTopics.get(mCurrentWordIndex).getSound()),false);
    }

    private void showCurrentCard(){
        getMvpView().showCardInfo(wordByTopics.get(mCurrentWordIndex), CommonUtils.getWordLang(getDataManager().getCurrentLang(), wordByTopics.get(mCurrentWordIndex)));
        getMvpView().showPageInfo((mCurrentWordIndex + 1) + "/" + (wordByTopics.size()));
    }
}
