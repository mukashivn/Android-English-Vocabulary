package com.core.app.data.prefs;

/**
 * Created by CuongCK on 2/13/17.
 */

public interface PreferencesHelper {
    //Define all method here
    void saveCurrentLang(String currentLang);

    String getCurrentLang();

    void setDonAgain();

    boolean getDontAgain();

    boolean isAllowShowRate();

    void countOpenApp();

    void resetOpenApp();

    void savePurchase(String key, boolean value);

    boolean isPurChase(String key);
}
