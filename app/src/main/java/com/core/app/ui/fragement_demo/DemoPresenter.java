package com.core.app.ui.fragement_demo;

import com.core.app.data.DataManager;
import com.core.app.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DemoPresenter<V extends IDemoView> extends BasePresenter<V> implements IDemoPresenter<V> {
    @Inject
    public DemoPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
