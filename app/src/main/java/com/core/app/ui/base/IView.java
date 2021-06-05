package com.core.app.ui.base;

import android.os.Bundle;
import androidx.annotation.StringRes;

import com.facebook.ads.NativeAd;

/**
 * Created by CuongCK on 2/13/17.
 */

public interface IView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    boolean isNetworkConnected();

    void hideKeyboard();

    void showNativeAds(NativeAd nativeAd);

    void logEvent(String event, Bundle bundle);

    void showExitTest();

    void showResultTest(int correctCount, int wrongCount);

    void showFullAds();
}
