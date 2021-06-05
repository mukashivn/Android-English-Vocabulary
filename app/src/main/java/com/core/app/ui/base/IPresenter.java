package com.core.app.ui.base;

import android.content.Context;

/**
 * Created by CuongCK on 2/13/17.
 */

public interface IPresenter<V extends IView> {
    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(int errorCode);

    void setUserAsLoggedOut();

    void loadNativeAds(Context context);

    void showFullAds(Context context);

    void playSound(Context context, int rawId, boolean isSlow);

}
