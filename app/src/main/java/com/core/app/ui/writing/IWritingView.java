package com.core.app.ui.writing;

import com.google.android.material.textfield.TextInputEditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.app.ui.base.IView;

public interface IWritingView extends IView {
    void showTitle(String mainTopic, String subTopic);

    void showResult(String correct, String wrong);

    void showQA(int imageId);

    ImageButton getCheckAnswer();

    ImageView getThumb();

    TextView getCorrectView();

    TextView getWrongView();

    TextInputEditText getEdtAnswer();

    void showResultTest(int mCountCorrect, int mCountWrong);

    TextView getCorrectAnswerView();

}
