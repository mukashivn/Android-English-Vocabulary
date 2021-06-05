package com.core.app.ui.home;

import com.core.app.data.wrapper.TopicWrapper;
import com.core.app.ui.base.IView;

import java.util.ArrayList;

public interface IHomeView extends IView {
    void showTopic(ArrayList<TopicWrapper> topics, String currentLang);

    void showBottomSheet();

    void showSubTopic(int id, int courseType);

    void updateLang(String lang);

    void showStore();

    void showGuestUpgradeDialog();
}
