package com.core.app.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.core.app.data.DataManager;
import com.core.app.data.db.Topic;
import com.core.app.ui.base.BasePresenter;
import com.core.app.utils.AppConstants;
import com.core.app.utils.CommonUtils;
import com.core.ssvapp.BuildConfig;
import com.core.ssvapp.R;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class HomePresenter<V extends IHomeView> extends BasePresenter<V> implements IHomePresenter<V> {
    private Context mContext;
    private int mCountNativeAds = 0;
    private Topic mCurrentTopic;

    private BillingProcessor bp;
    private boolean readyToPurchase = false;

    @Inject
    public HomePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void init(Context context) {
        mContext = context;

        //TODO: Check In-App-Purchase
        if (!BillingProcessor.isIabServiceAvailable(mContext)) {
            return;
        }
        initBilling();
        if (!getDataManager().isPurChase(mContext.getString(R.string.remove_ads))) {
            getMvpView().showGuestUpgradeDialog();
        }
    }

    private void initBilling() {
        bp = new BillingProcessor(mContext, BuildConfig.INAPP_BASE64, BuildConfig.INAPP_MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {

            }

            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) {

            }

            @Override
            public void onBillingInitialized() {
                readyToPurchase = true;
            }

            @Override
            public void onPurchaseHistoryRestored() {
                for (String sku : bp.listOwnedProducts())
                    getDataManager().savePurchase(sku, true);

            }
        });
    }

    @Override
    public void loadAds() {
        mCountNativeAds++;
        if (mCountNativeAds > 3) {
            return;
        }
        loadNativeAds(mContext);
    }


    @Override
    public void openBottomSheet(Topic topic) {
        mCurrentTopic = topic;
        getMvpView().showBottomSheet();
    }

    @Override
    public void loadTopic() {
        getMvpView().showTopic(CommonUtils.convertToTopicWapper(getDataManager().getListTopPic(), getDataManager().isPurChase(mContext.getString(R.string.remove_ads))), getDataManager().getCurrentLang());
    }

    @Override
    public void handleShowSubTopic(int which) {
        int courseType = AppConstants.COURSE_LEARN;
        switch (which) {
            case R.id.bot_learn:
                courseType = AppConstants.COURSE_LEARN;
                break;
            case R.id.bot_flashcard:
                courseType = AppConstants.COURSE_FLASH;
                break;
            case R.id.bot_test:
                courseType = AppConstants.COURSE_TEST;
                break;
            case R.id.bot_writing:
                courseType = AppConstants.COURSE_WRITING;
                break;
            case R.id.bot_listening:
                courseType = AppConstants.COURSE_LISTENING;
                break;
            case R.id.bot_speaking:
                courseType = AppConstants.COURSE_SPEAKING;
                break;
            case R.id.bot_test2:
                courseType = AppConstants.COURSE_TEST_2;
                break;
            case R.id.bot_listening2:
                courseType = AppConstants.COURSE_LISTENING_2;
                break;
        }
        //TODO: For test so comment it

        if (courseType == AppConstants.COURSE_TEST_2 || courseType == AppConstants.COURSE_LISTENING_2) {
            if (!getDataManager().isPurChase(mContext.getString(R.string.in_app_package_test_2))) {
                getMvpView().showStore();
                return;
            }

            if (!getDataManager().isPurChase(mContext.getString(R.string.in_app_pack_listen_2))) {
                getMvpView().showStore();
                return;
            }
        }


        getMvpView().showSubTopic(mCurrentTopic.getId(), courseType);
    }

    @Override
    public void reloadData() {
        if (!getDataManager().isPurChase(mContext.getString(R.string.remove_ads)))
            return;
        loadTopic();
    }

}
