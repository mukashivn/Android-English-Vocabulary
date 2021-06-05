package com.core.app.ui.listen;

import android.widget.ImageView;
import android.widget.TextView;

import com.core.app.ui.base.IView;

public interface IListenView extends IView {
    void showTitle(String mainTopic, String subTopic);

    void showResult(String correct, String wrong);

    void showQA(int imageId);

    ImageView getThumb();

    TextView getCorrectView();

    TextView getWrongView();

    void showResultTest(int mCountCorrect, int mCountWrong);

    TextView getCmdYes();

    TextView getCmdFalse();

    TextView getCorrectAnswerView();

}
