package com.core.app.ui.speak;

import android.widget.TextView;

import com.core.app.ui.base.IView;

public interface ISpeakView extends IView {
    void showTitle(String mainTopic, String subTopic);

    void showResult(String correct, String wrong);

    void showResultTest(int mCountCorrect, int mCountWrong);

    TextView getCorrectView();

    TextView getWrongView();

    void showQA(int id);

    void openVoice();

    TextView getYourVoice();

}
