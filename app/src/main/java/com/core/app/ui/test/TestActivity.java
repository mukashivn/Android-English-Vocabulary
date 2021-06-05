package com.core.app.ui.test;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.core.app.ui.base.BaseActivity;
import com.core.app.utils.AppConstants;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity implements ITestView {
    @Inject
    ITestPresenter<ITestView> mPresenenter;
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
    @BindView(R.id.answer_1)
    Button answer1;
    @BindView(R.id.answer_2)
    Button answer2;
    @BindView(R.id.answer_3)
    Button answer3;
    @BindView(R.id.answer_4)
    Button answer4;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void injectComponent() {
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenenter.onAttach(this);
    }

    @Override
    protected void init() {
        mPresenenter.init(this, getIntent().getStringExtra(AppConstants.EXTRA_TOPIC_TITLE), getIntent().getIntExtra(AppConstants.EXTRA_SUBTOPIC_ID, -1));
        mPresenenter.loadData();
    }

    @Override
    protected void loadAds() {

    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {

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
    public void showQA(int thumbId, String ans1, String ans2, String ans3, String ans4) {
        Glide.with(this).load(thumbId).into(this.thumbnail);
        answer1.setText(ans1);
        answer2.setText(ans2);
        answer3.setText(ans3);
        answer4.setText(ans4);

        answer1.setBackgroundResource(R.drawable.bg_item_topic);
        answer2.setBackgroundResource(R.drawable.bg_item_topic);
        answer3.setBackgroundResource(R.drawable.bg_item_topic);
        answer4.setBackgroundResource(R.drawable.bg_item_topic);

        answer1.setTextColor(getResources().getColor(R.color.text_color_test));
        answer2.setTextColor(getResources().getColor(R.color.text_color_test));
        answer3.setTextColor(getResources().getColor(R.color.text_color_test));
        answer4.setTextColor(getResources().getColor(R.color.text_color_test));

        enableOrDisable(true);
    }

    @Override
    public Button getAnswer1() {
        return answer1;
    }

    @Override
    public Button getAnswer2() {
        return answer2;
    }

    @Override
    public Button getAnswer3() {
        return answer3;
    }

    @Override
    public Button getAnswer4() {
        return answer4;
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
        super.showResultTest(mCountCorrect, mCountWrong);
    }

    protected void testAgain() {
        mPresenenter.restest();
    }

    @OnClick({R.id.action_back, R.id.answer_1, R.id.answer_2, R.id.answer_3, R.id.answer_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                showExitTest();
                break;
            case R.id.answer_1:
                mPresenenter.checkAnswer(answer1.getText(), answer1);
                enableOrDisable(false);
                break;
            case R.id.answer_2:
                mPresenenter.checkAnswer(answer2.getText(), answer2);
                enableOrDisable(false);
                break;
            case R.id.answer_3:
                mPresenenter.checkAnswer(answer3.getText(), answer3);
                enableOrDisable(false);
                break;
            case R.id.answer_4:
                mPresenenter.checkAnswer(answer4.getText(), answer4);
                enableOrDisable(false);
                break;
        }
    }

    private void enableOrDisable(boolean isEnable) {
        answer1.setEnabled(isEnable);
        answer2.setEnabled(isEnable);
        answer3.setEnabled(isEnable);
        answer4.setEnabled(isEnable);
    }

    @Override
    protected void onDestroy() {
        mPresenenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        showExitTest();
    }
}
