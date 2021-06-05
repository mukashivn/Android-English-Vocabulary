package com.core.app.ui.flash;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.core.app.data.db.WordByTopic;
import com.core.app.ui.animation.OnSwipeTouchListener;
import com.core.app.ui.base.BaseActivity;
import com.core.app.ui.custom.NativeAdsView;
import com.core.app.utils.AppConstants;
import com.core.app.utils.EnglisjViewUtils;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlashAcivity extends BaseActivity implements IFlashView {
    @Inject
    IFlashPresenter<IFlashView> mPresenter;
    @BindView(R.id.topic_title)
    TextView topicTitle;
    @BindView(R.id.subtopic_title)
    TextView subtopicTitle;
    @BindView(R.id.card_front)
    FrameLayout cardFront;
    @BindView(R.id.word_name)
    TextView wordName;
    @BindView(R.id.word_trans)
    TextView wordTrans;
    @BindView(R.id.word_lang)
    TextView wordLang;
    @BindView(R.id.card_back)
    FrameLayout cardBack;
    @BindView(R.id.root_cardview)
    RelativeLayout rootCardview;
    @BindView(R.id.page_info)
    TextView pageInfo;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.root)
    LinearLayout mRoot;
    @BindView(R.id.nativeAdsView)
    NativeAdsView mNativeAdView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_flash_card;
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
        cardBack.setOnTouchListener(new SwipeBackListener(this));
        cardFront.setOnTouchListener(new SwipeBackListener(this));
        rootCardview.setOnTouchListener(new SwipeBackListener(this));
        mRoot.setOnTouchListener(new SwipeBackListener(this));
        mPresenter.loadData();
    }

    @Override
    protected void loadAds() {
        mPresenter.loadNativeAds(this);
    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {
        Handler mainHandler = new Handler(getMainLooper());
        // This is your code
        Runnable myRunnable = () -> {
            mNativeAdView.setData(nativeAd);
        };
        mainHandler.post(myRunnable);

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void showTitle(String mainTopic, String subTopic) {
        topicTitle.setText(mainTopic);
        subtopicTitle.setText(subTopic);
        subtopicTitle.setVisibility(TextUtils.isEmpty(subTopic) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showCardInfo(WordByTopic topic, String lang) {
        showCardFront(topic);
        showCardBack(topic, lang);
    }

    @Override
    public FrameLayout getCardFront() {
        return cardFront;
    }

    @Override
    public FrameLayout getCardBack() {
        return cardBack;
    }

    @Override
    public RelativeLayout getRootFlash() {
        return rootCardview;
    }

    @Override
    public void showPageInfo(String info) {
        pageInfo.setText(info);
    }

    private void showCardFront(WordByTopic topic) {
        Glide.with(this).load(EnglisjViewUtils.getDrawableIDFromName(this, topic.getImageResourceId())).into(thumbnail);
    }

    private void showCardBack(WordByTopic topic, String lang) {
        wordName.setText(topic.getName());
        wordLang.setText(lang);
        wordLang.setVisibility(TextUtils.isEmpty(lang) ? View.GONE : View.VISIBLE);
        wordTrans.setText(topic.getTrans());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.action_back, R.id.cmd_prev_flash, R.id.cmd_flip_flash, R.id.cmd_next_flash, R.id.cmd_sound_normal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.cmd_prev_flash:
                mPresenter.previous();
                break;
            case R.id.cmd_flip_flash:
                mPresenter.flip();
                break;
            case R.id.cmd_next_flash:
                mPresenter.next();
                break;
            case R.id.cmd_sound_normal:
                mPresenter.play();
                break;
        }
    }

    public class SwipeBackListener extends OnSwipeTouchListener {
        public SwipeBackListener(Context context) {
            super(context);
        }

        @Override
        public void onSwipeTop() {
            mPresenter.flip2();
        }

        @Override
        public void onSwipeBottom() {
            mPresenter.flip();
        }

        @Override
        public void onSwipeRight() {
            mPresenter.previous();
        }

        @Override
        public void onSwipeLeft() {
            mPresenter.next();
        }
    }
}
