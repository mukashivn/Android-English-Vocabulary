package com.core.app.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.core.ssvapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Package: com.core.ssvapp.ui.rate
 * Created by: CuongCK
 * Date: 3/12/18
 */

public class DialogRateMe extends Dialog {
    @BindView(R.id.dont_again)
    Button dontAgain;
    @BindView(R.id.ask_later)
    Button askLater;
    private Context mContext;
    private OnDialogRateCB mCallBack;
    private boolean mOnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_rate_me);
        ButterKnife.bind(this);
    }

    public DialogRateMe(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DialogRateMe(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected DialogRateMe(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }


    @OnClick({R.id.cmd_rate_now, R.id.dont_again, R.id.ask_later})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cmd_rate_now:
                if (mCallBack != null) {
                    mCallBack.onRate(mOnExit);
                    dismiss();
                }
                break;
            case R.id.dont_again:
                if (mCallBack != null) {
                    mCallBack.onNoRate(true, mOnExit);
                    dismiss();
                }
                break;
            case R.id.ask_later:
                if (mCallBack != null) {
                    mCallBack.onNoRate(false, mOnExit);
                    dismiss();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        dontAgain.setVisibility(mOnExit ? View.VISIBLE : View.GONE);
        askLater.setVisibility(mOnExit ? View.VISIBLE : View.GONE);
    }

    public void show(boolean onExit){
        mOnExit = onExit;
        show();
    }

    public void setCallBack(OnDialogRateCB cb) {
        mCallBack = cb;
    }

    public void hideDontShowAgain(int vis) {
        dontAgain.setVisibility(vis);
    }

    public int getDontAgainVis(){
        return dontAgain.getVisibility();
    }

    public interface OnDialogRateCB {
        void onNoRate(boolean dontAgain, boolean onExit);

        void onRate(boolean onExit);

    }
}
