package com.core.app.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;

import com.core.app.ui.base.BaseActivity;
import com.core.app.ui.main.MainActivity;
import com.core.app.utils.AdsUtils;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements ISplashView {
    @Inject
    ISplashPresenter<ISplashView> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void injectComponent() {
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        setUnBinder(ButterKnife.bind(this));
    }

    @Override
    protected void init() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            //startActivity(new Intent(SplashActivity.this, DemoActivity.class));
            finish();
        }, 2000);
    }

    @Override
    protected void loadAds() {
        initAdmodMobileAdvertising();
    }

    private void initAdmodMobileAdvertising() {
        MobileAds.initialize(this, initializationStatus -> {
            setMobileAdConfig();
            AdsUtils.loadFullScreenAdvertising(this);
        });

    }

    private void setMobileAdConfig() {
        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("0762054F2335D7DE3E7CB435599E520D")).build();
        MobileAds.setRequestConfiguration(requestConfiguration);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {

    }

}
