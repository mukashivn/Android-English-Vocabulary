package com.core.app.ui.speak;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class SpeakActivity extends BaseActivity implements ISpeakView {
    @Inject
    ISpeakPresenter<ISpeakView> mPresenter;
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
    @BindView(R.id.your_voice)
    TextView mYourVoice;
    @BindView(R.id.nativeAdsView)
    NativeAdsView mNativeAdsView;

    private static final int REQUEST_VOICE = 1001;

    @Override
    public int getLayoutId() {
        return R.layout.activity_speak;
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
        mNativeAdsView.setData(nativeAd);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.action_back, R.id.cmd_voidce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                showExitTest();
                break;
            case R.id.cmd_voidce:
                openVoice();
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
    public void showResultTest(int mCountCorrect, int mCountWrong) {
        super.showResultTest(mCountCorrect, mCountWrong);
    }

    protected void testAgain() {
        mPresenter.restest();
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
    public void showQA(int id) {
        Glide.with(this).load(id).into(this.thumbnail);
    }

    @Override
    public void openVoice() {
        try {
            Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
            intent.putExtra("android.speech.extra.LANGUAGE", "en-US");
            startActivityForResult(intent, REQUEST_VOICE);

        } catch (ActivityNotFoundException e) {
            onError("Please install voice");
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + "com.google.android.googlequicksearchbox")));
        }
    }

    @Override
    public TextView getYourVoice() {
        return mYourVoice;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VOICE && resultCode == RESULT_OK) {
            String ansewer = ((String) data.getStringArrayListExtra("android.speech.extra.RESULTS").get(0)).toLowerCase();
            mPresenter.checkAnswer(ansewer);
        }
    }

    @Override
    public void onBackPressed() {
        showExitTest();
    }
}
