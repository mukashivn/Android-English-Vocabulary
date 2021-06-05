package com.core.app.ui.listen;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.core.app.ui.base.BaseActivity;
import com.core.app.ui.custom.NativeAdsView;
import com.core.app.utils.AppConstants;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListenActivity extends BaseActivity implements IListenView {
    @Inject
    IListenPresenter<IListenView> mPresenter;
    @BindView(R.id.topic_title)
    TextView topicTitle;
    @BindView(R.id.subtopic_title)
    TextView subtopicTitle;
    @BindView(R.id.ads_root)
    FrameLayout adsRoot;
    @BindView(R.id.correct_count)
    TextView correctCount;
    @BindView(R.id.wrong_count)
    TextView wrongCount;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.cmd_true)
    TextView cmdTrue;
    @BindView(R.id.cmd_false)
    TextView cmdFalse;
    @BindView(R.id.correct_answer)
    TextView correctAnswer;
    @BindView(R.id.nativeAdsView)
    NativeAdsView mNativeAdView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_listening_1;
    }

    @Override
    public void injectComponent() {
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);
    }

    @Override
    protected void init() {
        mPresenter.init(this, getIntent().getStringExtra(AppConstants.EXTRA_TOPIC_TITLE), getIntent().getIntExtra(AppConstants.EXTRA_SUBTOPIC_ID, -1));
        mPresenter.loadData();
    }

    @Override
    protected void loadAds() {
        mPresenter.loadNativeAds(this);
    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {
        mNativeAdView.setData(nativeAd);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @OnClick({R.id.action_back, R.id.play_sound, R.id.cmd_true, R.id.cmd_false})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                showExitTest();
                break;
            case R.id.play_sound:
                mPresenter.play();
                break;
            case R.id.cmd_true:
                mPresenter.checkAnswer(true);
                cmdTrue.setEnabled(false);
                cmdFalse.setEnabled(false);
                break;
            case R.id.cmd_false:
                mPresenter.checkAnswer(false);
                cmdTrue.setEnabled(false);
                cmdFalse.setEnabled(false);
                break;
        }
    }

    @Override
    public void showTitle(String mainTopic, String subTopic) {
        topicTitle.setText(mainTopic);
        subtopicTitle.setText(subTopic);
        subtopicTitle.setVisibility(TextUtils.isEmpty(subTopic) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showResult(String correct, String wrong) {
        correctCount.setText(correct);
        wrongCount.setText(wrong);
    }

    @Override
    public void showQA(int imageId) {
        Glide.with(this).load(imageId).into(thumbnail);
        cmdTrue.setEnabled(true);
        cmdFalse.setEnabled(true);
    }

    @Override
    public ImageView getThumb() {
        return thumbnail;
    }

    @Override
    public TextView getCorrectView() {
        return correctCount;
    }

    @Override
    public TextView getWrongView() {
        return wrongCount;
    }

    @Override
    public void showResultTest(int mCountCorrect, int mCountWrong) {
        super.showResultTest(mCountCorrect,mCountWrong);
    }

    protected void testAgain() {
        mPresenter.restest();
    }

    @Override
    public TextView getCmdYes() {
        return cmdTrue;
    }

    @Override
    public TextView getCmdFalse() {
        return cmdFalse;
    }

    @Override
    public TextView getCorrectAnswerView() {
        return correctAnswer;
    }

    @Override
    public void onBackPressed() {
        showExitTest();
    }

}
