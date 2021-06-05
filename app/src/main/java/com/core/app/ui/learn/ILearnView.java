package com.core.app.ui.learn;

import com.core.app.data.wrapper.WordWrapper;
import com.core.app.ui.base.IView;

import java.util.ArrayList;

public interface ILearnView extends IView {
    void showTitle(String mainTopic, String subTopic);

    void showWords(ArrayList<WordWrapper> wordWrappers, String lang);
}
