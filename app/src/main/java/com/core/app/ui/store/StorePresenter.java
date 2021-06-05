package com.core.app.ui.store;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.core.app.data.DataManager;
import com.core.app.ui.base.BaseActivity;
import com.core.app.ui.base.BasePresenter;
import com.core.ssvapp.BuildConfig;
import com.core.ssvapp.R;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class StorePresenter<V extends IStoreView> extends BasePresenter<V> implements IStorePresenter<V>, BillingProcessor.IBillingHandler {
    private BaseActivity mContext;
    private BillingProcessor mBillProcess;
    private boolean readyToPurchase = false;

    @Inject
    public StorePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void init(BaseActivity activity) {
        this.mContext = activity;
        //checkSupportBilling();
        mBillProcess = new BillingProcessor(mContext, BuildConfig.INAPP_BASE64,BuildConfig.INAPP_MERCHANT_ID,this);

    }

    @Override
    public void removeAds() {
        if(getDataManager().isPurChase(mContext.getString(R.string.in_app_remove_ads))) {
            getMvpView().onError(R.string.just_purchased);
            return;
        }
        checkSupportBilling();
        mBillProcess.purchase(mContext, mContext.getString(R.string.in_app_remove_ads));
    }

    @Override
    public void buyPackageTest() {
        if(getDataManager().isPurChase(mContext.getString(R.string.in_app_package_test_2))) {
            getMvpView().onError(R.string.just_purchased);
            return;
        }
        checkSupportBilling();
        mBillProcess.purchase(mContext, mContext.getString(R.string.in_app_package_test_2));
    }

    @Override
    public void buyPackageListen() {
        if(getDataManager().isPurChase(mContext.getString(R.string.in_app_pack_listen_2))) {
            getMvpView().onError(R.string.just_purchased);
            return;
        }
        checkSupportBilling();
        mBillProcess.purchase(mContext, mContext.getString(R.string.in_app_pack_listen_2));
    }

    @Override
    public void handlePurchase(int requestCode, int resultCode, Intent data) {
        mBillProcess.handleActivityResult(requestCode, resultCode, data);
    }

    private boolean isSupportBilling() {
        return BillingProcessor.isIabServiceAvailable(mContext);
    }

    private void checkSupportBilling() {
        if(!isSupportBilling()){
            getMvpView().onError(R.string.billing_not_avaiavle);
            return;
        }

        if(!readyToPurchase){
            getMvpView().onError(R.string.billing_not_init);
        }
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        getDataManager().savePurchase(productId,true);
    }

    @Override
    public void onPurchaseHistoryRestored() {
        for (String sku: mBillProcess.listOwnedProducts()){
            getDataManager().savePurchase(sku,true);
        }
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        getMvpView().onError(R.string.error_purchase);

    }

    @Override
    public void onBillingInitialized() {
        readyToPurchase = true;
    }

    @Override
    public void onDetach() {
        if(mBillProcess != null){
            mBillProcess.release();
        }
        super.onDetach();
    }
}
