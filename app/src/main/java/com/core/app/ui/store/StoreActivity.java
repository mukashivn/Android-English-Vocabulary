package com.core.app.ui.store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.core.app.ui.base.BaseActivity;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreActivity extends BaseActivity implements IStoreView {
    @Inject
    IStorePresenter<IStoreView> mPresenter;
    @BindView(R.id.topic_title)
    TextView topicTitle;
    @BindView(R.id.subtopic_title)
    TextView subtopicTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    public void injectComponent() {
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);
    }

    @Override
    protected void init() {
        topicTitle.setText(getString(R.string.nav_store));
        subtopicTitle.setVisibility(View.GONE);
        mPresenter.init(this);
    }

    @Override
    protected void loadAds() {

    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {

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

    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @OnClick({R.id.buy_remove_ads, R.id.buy_learn_verbs, R.id.buy_more_verbs, R.id.layout_remove_ads, R.id.layout_test_2, R.id.layout_listen_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_remove_ads:
            case R.id.buy_remove_ads:
                mPresenter.removeAds();
                break;
            case R.id.layout_test_2:
            case R.id.buy_learn_verbs:
                mPresenter.buyPackageTest();
                break;
            case R.id.layout_listen_2:
            case R.id.buy_more_verbs:
                mPresenter.buyPackageListen();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.handlePurchase(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
