package com.core.app.ui.fragement_demo;

import com.core.app.di.PerActivity;
import com.core.app.ui.base.IPresenter;

@PerActivity
public interface IDemoPresenter<V extends IDemoView> extends IPresenter<V> {
}
