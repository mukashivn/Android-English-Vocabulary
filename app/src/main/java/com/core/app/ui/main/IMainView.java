package com.core.app.ui.main;

import com.core.app.ui.base.IView;

public interface IMainView extends IView {
    void updateFlag(int currentFlag);

    void updateLang(String lang);

    void showDialogAskRate(boolean onExit);

    void exitApp();

    void openAppInStore(String applicationId);

    void showAskDialog();

    void exitAppNow();
}
