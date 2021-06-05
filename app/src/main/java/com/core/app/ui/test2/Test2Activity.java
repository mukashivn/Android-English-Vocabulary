package com.core.app.ui.test2;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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

public class Test2Activity extends BaseActivity implements ITest2View {
    @BindView(R.id.action_back)
    ImageButton actionBack;
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
    @BindView(R.id.answer_correct)
    TextView answerCorrect;
    @BindView(R.id.answer_1)
    ImageView answer1;
    @BindView(R.id.answer_2)
    ImageView answer2;
    @BindView(R.id.layout_answer1)
    FrameLayout layoutAnswer1;
    @BindView(R.id.layout_answer2)
    FrameLayout layoutAnswer2;

    @Inject
    ITest2Presenter<ITest2View> mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test2;
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

    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {

    }

    @OnClick({R.id.action_back, R.id.layout_answer1, R.id.layout_answer2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                showExitTest();
                break;
            case R.id.layout_answer1:
                mPresenter.checkAnswer(0);
                layoutAnswer1.setEnabled(false);
                layoutAnswer2.setEnabled(false);
                break;
            case R.id.layout_answer2:
                mPresenter.checkAnswer(1);
                layoutAnswer1.setEnabled(false);
                layoutAnswer2.setEnabled(false);
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
        super.showResultTest(mCountCorrect,mCountWrong);
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
    public FrameLayout getFrameAnswer1() {
        return layoutAnswer1;
    }

    @Override
    public FrameLayout getFrameAnswer2() {
        return layoutAnswer2;
    }

    @Override
    public void showQA(String wordName, int answer1, int answer2) {
        layoutAnswer1.setBackgroundResource(R.drawable.bg_rect_gray);
        layoutAnswer2.setBackgroundResource(R.drawable.bg_rect_gray);
        answerCorrect.setText(wordName);
        Glide.with(this).load(answer1).into(this.answer1);
        Glide.with(this).load(answer2).into(this.answer2);
        layoutAnswer1.setEnabled(true);
        layoutAnswer2.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        showExitTest();
    }


}
