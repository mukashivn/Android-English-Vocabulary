package com.core.app.di.module;

import android.app.Application;
import android.content.Context;

import com.core.app.data.AppDataManager;
import com.core.app.data.DataManager;
import com.core.app.data.prefs.AppPreferencesHelper;
import com.core.app.data.prefs.PreferencesHelper;
import com.core.app.data.realm.AppRealmHelper;
import com.core.app.data.realm.RealmHelper;
import com.core.ssvapp.BuildConfig;
import com.core.ssvapp.R;
import com.core.app.data.network.ApiHelper;
import com.core.app.di.ApiInfo;
import com.core.app.di.ApplicationContext;
import com.core.app.di.DomainInfo;
import com.core.app.di.PreferenceInfo;
import com.core.app.di.WebDomainInfo;
import com.core.app.utils.AppConstants;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CuongCK on 2/13/17.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }


    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @DomainInfo
    String provideDomainInfo() {
        return BuildConfig.BASE_URL;
    }

    @Provides
    @WebDomainInfo
    String provideWebDomainInfo() {
        return BuildConfig.BASE_WEB;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    RealmHelper provideRealmHelper(AppRealmHelper appRealmHelper) {
        return appRealmHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/Montserrat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient providerOkhttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return okHttpClient;
    }

    @Provides
    @Singleton
    Gson providerGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient, @DomainInfo String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    ApiHelper provideRestRequest(Retrofit retrofit) {
        return retrofit.create(ApiHelper.class);
    }
}
