package com.core.app.di.component;

import com.core.app.di.PerActivity;
import com.core.app.di.module.ActivityModule;
import com.core.app.ui.flash.FlashAcivity;
import com.core.app.ui.fragement_demo.DemoFragment;
import com.core.app.ui.home.HomeFragment;
import com.core.app.ui.learn.LearnActivity;
import com.core.app.ui.listen.ListenActivity;
import com.core.app.ui.listen2.Listen2Activity;
import com.core.app.ui.main.MainActivity;
import com.core.app.ui.speak.SpeakActivity;
import com.core.app.ui.splash.SplashActivity;
import com.core.app.ui.store.StoreActivity;
import com.core.app.ui.test.TestActivity;
import com.core.app.ui.test2.Test2Activity;
import com.core.app.ui.topiclist.SubTopicActivity;
import com.core.app.ui.writing.WritingActivity;

import dagger.Component;

/**
 * Created by CuongCK on 2/13/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(SplashActivity activity);

    void inject(DemoFragment fragment);

    void inject(HomeFragment fragment);

    void inject(SubTopicActivity activity);

    void inject(LearnActivity activity);

    void inject(FlashAcivity acivity);

    void inject(ListenActivity activity);

    void inject(SpeakActivity activity);

    void inject(TestActivity activity);

    void inject(WritingActivity activity);

    void inject(Test2Activity activity);

    void inject(Listen2Activity activity);

    void inject(StoreActivity activity);

}
