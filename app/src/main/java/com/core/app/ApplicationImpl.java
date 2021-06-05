package com.core.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.multidex.MultiDexApplication;

import com.core.app.di.component.ApplicationComponent;
import com.core.app.di.component.DaggerApplicationComponent;
import com.core.app.di.module.ApplicationModule;
import com.core.app.utils.AppConstants;
import com.core.app.utils.LogUtils;
import com.facebook.ads.AdSettings;

import javax.inject.Inject;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.realm.Realm;

/**
 * Created by CuongCK on 2/13/17.
 */

public class ApplicationImpl extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {
    @Inject
    CalligraphyConfig mCalligraphyConfig;
    private ApplicationComponent mApplicationComponent;
    private int numStarted;
    public static boolean isForeground;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        mCalligraphyConfig)).build());
        AdSettings.addTestDevice("5cd2cd38-7b83-4e08-a5e9-5b6c4d440037");
        registerActivityLifecycleCallbacks(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtils.eTag(AppConstants.APP_TAG, "onActivityStarted -> " + activity.getLocalClassName());
        if (numStarted == 0) {
            isForeground = true;
        }
        numStarted++;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtils.eTag(AppConstants.APP_TAG, "onActivityDestroyed -> " + activity.getLocalClassName());
        numStarted--;
        if (numStarted <= 0) {
            isForeground = false;
        }
    }
}
