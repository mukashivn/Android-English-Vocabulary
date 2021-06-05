package com.core.app.ui.listen2;

import android.widget.FrameLayout;
import android.widget.TextView;

import com.core.app.ui.base.IView;

public interface IListen2View extends IView{
    void showTitle(String mainTopic, String subTopic);

    void showResult(String correct, String wrong);

    void showResultTest(int mCountCorrect, int mCountWrong);

    TextView getCorrectView();

    TextView getWrongView();

    FrameLayout getFrameAnswer1();

    FrameLayout getFrameAnswer2();

    void showQA(int answer1, int answer2);

}
