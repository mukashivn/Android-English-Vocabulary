package com.core.app.ui.store;

import android.content.Intent;

import com.core.app.di.PerActivity;
import com.core.app.ui.base.BaseActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface IStorePresenter<V extends IStoreView>  extends IPresenter<V> {
    void init(BaseActivity activity);

    void removeAds();

    void buyPackageTest();

    void buyPackageListen();

    void handlePurchase(int requestCode, int resultCode, Intent data);
}
