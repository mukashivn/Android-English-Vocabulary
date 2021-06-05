package com.core.app.ui.base;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;

import com.core.app.data.DataManager;
import com.core.ssvapp.R;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by CuongCK on 2/13/17.
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {

    private final DataManager mDataManager;

    private final CompositeDisposable compositeDisposable;

    private V mMvpView;

    private MediaPlayer mPlayer;

    @Inject
    public BasePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    @Override
    public void handleApiError(int errorCode) {

    }

    @Override
    public void setUserAsLoggedOut() {

    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    //Load Ads
    public void loadNativeAds(Context context) {
        if (getDataManager().isPurChase(context.getString(R.string.in_app_remove_ads)))
            return;
    }

    @Override
    public void showFullAds(Context context) {
        if (getDataManager().isPurChase(context.getString(R.string.in_app_remove_ads)))
            return;
        if (getMvpView() != null)
            getMvpView().showFullAds();
    }


    @Override
    public void playSound(Context context, int rawId, boolean isSlow) {
        if (mPlayer != null) {
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }

        mPlayer = MediaPlayer.create(context, rawId);
        if (isSlow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mPlayer.setPlaybackParams(mPlayer.getPlaybackParams().setSpeed(0.5f));
            }
        }

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (mPlayer != null) {
                try {
                    mPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 200);

    }

}
