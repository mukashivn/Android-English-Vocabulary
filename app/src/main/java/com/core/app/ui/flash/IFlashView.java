package com.core.app.ui.flash;

import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.core.app.data.db.WordByTopic;
import com.core.app.ui.base.IView;

public interface IFlashView extends IView {
    void showTitle(String mainTopic, String subTopic);

    void showCardInfo(WordByTopic topic, String lang);

    FrameLayout getCardFront();

    FrameLayout getCardBack();

    RelativeLayout getRootFlash();

    void showPageInfo(String pageInfo);

}
