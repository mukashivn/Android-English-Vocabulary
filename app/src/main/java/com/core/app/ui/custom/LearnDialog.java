package com.core.app.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.app.data.db.WordByTopic;
import com.core.app.utils.EnglisjViewUtils;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LearnDialog extends Dialog {
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.how_to_read)
    TextView howToRead;
    @BindView(R.id.word_meaning)
    TextView wordMeaning;
    @BindView(R.id.example_sentence)
    TextView exampleSentence;
    @BindView(R.id.word_name)
    TextView wordName;
    @BindView(R.id.ads_root)
    NativeAdsView adsRoot;
    @BindView(R.id.cmd_sound_slow)
    ImageButton cmdSoundSlow;

    private Context mContext;
    private ILearnListener mCallBack;
    private WordByTopic mWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_bottom_learn);
        ButterKnife.bind(this);
        Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.CENTER);
        setCancelable(false);
    }

    public LearnDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LearnDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected LearnDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    @Override
    protected void onStart() {
        super.onStart();
        inflateData();
    }

    private void init(Context context) {
        this.mContext = context;

    }

    @OnClick({R.id.cmd_dissmis, R.id.cmd_sound_normal, R.id.cmd_sound_slow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cmd_dissmis:
                dismiss();
                break;
            case R.id.cmd_sound_normal:
                if (mCallBack != null) {
                    mCallBack.onListening(false, mWord.getSound());
                }
                break;
            case R.id.cmd_sound_slow:
                if (mCallBack != null) {
                    mCallBack.onListening(true, mWord.getSound());
                }
                break;
        }
    }

    public void show(WordByTopic data) {
        mWord = data;
        show();
    }

    public void setCallBack(ILearnListener listener) {
        this.mCallBack = listener;
    }

    public void setNativeAds(NativeAd nativeAds){
        adsRoot.setData(nativeAds);
    }

    private void inflateData() {
        thumbnail.setImageResource(EnglisjViewUtils.getDrawableIDFromName(mContext, mWord.getImageResourceId()));
        wordName.setText(mWord.getName());
        howToRead.setText(mWord.getTrans());
        wordMeaning.setText(mWord.getDesc());
        exampleSentence.setText(mWord.getUsage());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cmdSoundSlow.setVisibility(View.VISIBLE);
        }else {
            cmdSoundSlow.setVisibility(View.GONE);
        }
    }

    public interface ILearnListener {
        void onListening(boolean isSlow, String sound);
    }
}
