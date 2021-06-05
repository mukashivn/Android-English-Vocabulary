package com.core.app.di.component;

import android.app.Application;
import android.content.Context;

import com.core.app.data.DataManager;
import com.core.app.ApplicationImpl;
import com.core.app.di.ApplicationContext;
import com.core.app.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by CuongCK on 2/13/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(ApplicationImpl app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
