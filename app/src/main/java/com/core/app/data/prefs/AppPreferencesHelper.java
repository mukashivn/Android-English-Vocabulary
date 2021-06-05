package com.core.app.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.core.app.di.ApplicationContext;
import com.core.app.di.PreferenceInfo;
import com.core.app.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by CuongCK on 2/13/17.
 */
@Singleton
public class AppPreferencesHelper implements PreferencesHelper {
    private final SharedPreferences mPrefs;
    private static final String PREF_CURRENT_LANG = "PREF_CURRENT_LANG";
    private final String PREF_DONT_AGAIN = "PREF_DONT_AGAIN";
    private final String PREF_COUNT_OPEN_APP = "PREF_COUNT_OPEN_APP";

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void saveCurrentLang(String currentLang) {
        mPrefs.edit().putString(PREF_CURRENT_LANG,currentLang).apply();
    }

    @Override
    public String getCurrentLang() {
        return mPrefs.getString(PREF_CURRENT_LANG, AppConstants.LANG_EN);
    }

    @Override
    public void setDonAgain() {
        mPrefs.edit().putBoolean(PREF_DONT_AGAIN, true).apply();
    }

    @Override
    public boolean getDontAgain() {
        return mPrefs.getBoolean(PREF_DONT_AGAIN, false);
    }

    @Override
    public boolean isAllowShowRate() {
        return mPrefs.getInt(PREF_COUNT_OPEN_APP, 0) >= AppConstants.OPEN_APP_PERIOD;
    }

    @Override
    public void countOpenApp() {
        int current = mPrefs.getInt(PREF_COUNT_OPEN_APP, 0) + 1;
        mPrefs.edit().putInt(PREF_COUNT_OPEN_APP, current).apply();
    }

    @Override
    public void resetOpenApp() {
        mPrefs.edit().putInt(PREF_COUNT_OPEN_APP, 0).apply();
    }

    @Override
    public void savePurchase(String key, boolean value) {
        mPrefs.edit().putBoolean(key,value).apply();
    }

    @Override
    public boolean isPurChase(String key) {
        return mPrefs.getBoolean(key,false);
    }
}
