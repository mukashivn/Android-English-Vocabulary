package com.core.app.di.module;

import android.app.Activity;
import android.content.Context;

import com.core.app.di.ActivityContext;
import com.core.app.ui.flash.FlashPresenter;
import com.core.app.ui.flash.IFlashPresenter;
import com.core.app.ui.flash.IFlashView;
import com.core.app.ui.fragement_demo.DemoPresenter;
import com.core.app.ui.fragement_demo.IDemoPresenter;
import com.core.app.ui.fragement_demo.IDemoView;
import com.core.app.ui.home.HomePresenter;
import com.core.app.ui.home.IHomePresenter;
import com.core.app.ui.home.IHomeView;
import com.core.app.ui.learn.ILearnPresenter;
import com.core.app.ui.learn.ILearnView;
import com.core.app.ui.learn.LearnPresenter;
import com.core.app.ui.listen.IListenPresenter;
import com.core.app.ui.listen.IListenView;
import com.core.app.ui.listen.ListenPresenter;
import com.core.app.ui.listen2.IListen2Presenter;
import com.core.app.ui.listen2.IListen2View;
import com.core.app.ui.listen2.Listen2Presenter;
import com.core.app.ui.main.IMainPresenter;
import com.core.app.ui.main.IMainView;
import com.core.app.ui.main.MainPresenter;
import com.core.app.ui.speak.ISpeakPresenter;
import com.core.app.ui.speak.ISpeakView;
import com.core.app.ui.speak.SpeakPresenter;
import com.core.app.ui.splash.ISplashPresenter;
import com.core.app.ui.splash.ISplashView;
import com.core.app.ui.splash.SplashPresenter;
import com.core.app.ui.store.IStorePresenter;
import com.core.app.ui.store.IStoreView;
import com.core.app.ui.store.StorePresenter;
import com.core.app.ui.test.ITestPresenter;
import com.core.app.ui.test.ITestView;
import com.core.app.ui.test.TestPresenter;
import com.core.app.ui.test2.ITest2Presenter;
import com.core.app.ui.test2.ITest2View;
import com.core.app.ui.test2.Test2Presenter;
import com.core.app.ui.topiclist.ISubTopicPresenter;
import com.core.app.ui.topiclist.ISubTopicView;
import com.core.app.ui.topiclist.SubTopicPresenter;
import com.core.app.ui.writing.IWritingPresenter;
import com.core.app.ui.writing.IWritingView;
import com.core.app.ui.writing.WritingPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by CuongCK on 2/13/17.
 */
@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    //Decleare presenter here
    @Provides
    IMainPresenter<IMainView> provideMainPrenter(MainPresenter<IMainView> mainPresenter) {
        return mainPresenter;
    }

    @Provides
    ISplashPresenter<ISplashView> provideSplashPresenter(SplashPresenter<ISplashView> splashPresenter) {
        return splashPresenter;
    }

    @Provides
    IDemoPresenter<IDemoView> provideDemoPresenter(DemoPresenter<IDemoView> demoPresenter){
        return demoPresenter;
    }

    @Provides
    IHomePresenter<IHomeView> provideHomePresenter(HomePresenter<IHomeView> homePresenter){
        return homePresenter;
    }

    @Provides
    ISubTopicPresenter<ISubTopicView> provideSubTopicPresenter(SubTopicPresenter<ISubTopicView> presenter){
        return presenter;
    }

    @Provides
    ILearnPresenter<ILearnView> provideLearnPresenter(LearnPresenter<ILearnView> presenter){
        return presenter;
    }

    @Provides
    IFlashPresenter<IFlashView> provideFlashPresenter(FlashPresenter<IFlashView> presenter){
        return presenter;
    }

    @Provides
    IListenPresenter<IListenView> provideListentPresenter(ListenPresenter<IListenView> presenter){
        return presenter;
    }

    @Provides
    ISpeakPresenter<ISpeakView> provideSpeakPresenter(SpeakPresenter<ISpeakView> presenter){
        return presenter;
    }

    @Provides
    ITestPresenter<ITestView> provideTestPresenter(TestPresenter<ITestView> pre){
        return pre;
    }

    @Provides
    IWritingPresenter<IWritingView> provideWritingPresenter(WritingPresenter<IWritingView> pre){
        return pre;
    }

    @Provides
    ITest2Presenter<ITest2View> provideTest2Presenter(Test2Presenter<ITest2View> test2Presenter){
        return test2Presenter;
    }

    @Provides
    IListen2Presenter<IListen2View> provideListen2Presenter(Listen2Presenter<IListen2View> listen2Presenter){
        return listen2Presenter;
    }

    @Provides
    IStorePresenter<IStoreView> provideStorePresenter(StorePresenter<IStoreView> storePresenter) {
        return storePresenter;
    }
}
