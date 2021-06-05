package com.core.app.ui.main;

import android.content.Context;

import com.core.app.data.DataManager;
import com.core.app.ui.base.BasePresenter;
import com.core.app.utils.AppConstants;
import com.core.ssvapp.BuildConfig;
import com.core.ssvapp.R;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter<V extends IMainView> extends BasePresenter<V> implements IMainPresenter<V> {
    private Context mContext;

    @Inject
    public MainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void init(Context context) {
        mContext = context;
        AppConstants.IS_REMOVE_ADS = getDataManager().isPurChase(context.getString(R.string.remove_ads));
        getMvpView().updateFlag(getFlagId(getDataManager().getCurrentLang()));
        getDataManager().countOpenApp();
    }

    @Override
    public void changeLang(String langBra) {
        getDataManager().saveCurrentLang(langBra);
        getMvpView().updateFlag(getFlagId(langBra));
        getMvpView().updateLang(langBra);
    }

    @Override
    public void onRateComplete(boolean isExit, boolean isDontAgain, boolean isRate) {
        if (isDontAgain) {
            getDataManager().setDonAgain();
        }

        if (isExit) {
            if (isRate) {
                getMvpView().openAppInStore(BuildConfig.APPLICATION_ID);
            }
            getMvpView().exitApp();
        } else {
            if (isRate)
                getMvpView().openAppInStore(BuildConfig.APPLICATION_ID);
        }
    }

    @Override
    public void handleExitApp() {
        if (!getDataManager().getDontAgain() && getDataManager().isAllowShowRate()) {
            getDataManager().resetOpenApp();
            getMvpView().showAskDialog();
        } else {
            getMvpView().exitAppNow();
        }
    }

    @Override
    public void rateMe(boolean b) {
        getMvpView().showDialogAskRate(b);
    }

    @Override
    public void handleShowFullAds() {
        if (!AppConstants.IS_REMOVE_ADS) {
            getMvpView().showFullAds();
        }
    }

    private int getFlagId(String lang) {
        switch (lang) {
            case AppConstants.LANG_CHI:
                return R.drawable.china;
            case AppConstants.LANG_ESP:
                return R.drawable.spain;
            case AppConstants.LANG_FRA:
                return R.drawable.france;
            case AppConstants.LANG_GER:
                return R.drawable.germany;
            case AppConstants.LANG_HIN:
                return R.drawable.india;
            case AppConstants.LANG_ITA:
                return R.drawable.saudi_arabia;
            case AppConstants.LANG_JAP:
                return R.drawable.japan;
            case AppConstants.LANG_KOR:
                return R.drawable.south_korea;
            case AppConstants.LANG_POR:
                return R.drawable.brazil;
            case AppConstants.LANG_RUS:
                return R.drawable.russia;
            case AppConstants.LANG_TUR:
                return R.drawable.turkey;

            default:
                return R.drawable.ac_countrys_flags;
        }
    }
}
