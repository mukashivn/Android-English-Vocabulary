package com.core.app.ui.test;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.app.ui.base.IView;

public interface ITestView extends IView {
    void showTitle(String mainTopic, String subTopic);

    void showResult(String correct, String wrong);

    void showQA(int thumbId, String ans1, String ans2, String ans3, String ans4);

    Button getAnswer1();

    Button getAnswer2();

    Button getAnswer3();

    Button getAnswer4();

    ImageView getThumb();

    TextView getCorrectView();

    TextView getWrongView();

    void showResultTest(int mCountCorrect, int mCountWrong);

}
