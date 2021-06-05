package com.core.app.ui.fragement_demo;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.core.app.ui.base.BaseActivity;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoActivity extends BaseActivity {
    @BindView(R.id.container)
    FrameLayout container;

    @Override
    public int getLayoutId() {
        return R.layout.activity_demo;
    }

    @Override
    public void injectComponent() {

    }

    @Override
    protected void init() {
        addFragment(DemoFragment.newInstance(),DemoFragment.class.getName(),R.id.container);
    }

    @Override
    protected void loadAds() {

    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
