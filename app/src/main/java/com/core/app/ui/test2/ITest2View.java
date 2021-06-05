package com.core.app.ui.test2;

import android.widget.FrameLayout;
import android.widget.TextView;

import com.core.app.ui.base.IView;

public interface ITest2View extends IView {
    void showTitle(String mainTopic, String subTopic);

    void showResult(String correct, String wrong);

    void showResultTest(int mCountCorrect, int mCountWrong);

    TextView getCorrectView();

    TextView getWrongView();

    FrameLayout getFrameAnswer1();

    FrameLayout getFrameAnswer2();

    void showQA(String wordName, int answer1, int answer2);

}
