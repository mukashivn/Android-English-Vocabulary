package com.core.app.ui.splash;

import com.core.app.data.DataManager;
import com.core.app.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SplashPresenter<V extends ISplashView> extends BasePresenter<V> implements ISplashPresenter<V> {
    @Inject
    public SplashPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
