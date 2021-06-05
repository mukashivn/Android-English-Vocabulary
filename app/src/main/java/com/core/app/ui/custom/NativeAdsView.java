package com.core.app.ui.custom;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;
import com.santalu.aspectratioimageview.AspectRatioImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NativeAdsView extends CardView {
    @BindView(R.id.ads_sponsor_type)
    public TextView adsSponsorType;
    @BindView(R.id.ads_adchoice)
    public AspectRatioImageView adsAdchoice;
    @BindView(R.id.layout_sponsor)
    public LinearLayout layoutSponsor;
    @BindView(R.id.ads_cover)
    public AspectRatioImageView adsCover;
    @BindView(R.id.layout_thumb)
    public FrameLayout layoutThumb;
    @BindView(R.id.ads_icon)
    public AspectRatioImageView adsIcon;
    @BindView(R.id.ads_sponsor_name)
    public TextView adsSponsorName;
    @BindView(R.id.ads_title)
    public TextView adsTitle;
    @BindView(R.id.ads_install)
    public Button adsInstall;
    @BindView(R.id.ads_root)
    public CardView adsRoot;
    private Context mContext;
    private NativeAd mNativeAds;

    public NativeAdsView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public NativeAdsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NativeAdsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_native_ads, this, true);
        ButterKnife.bind(this, view);
        setVisibility(View.GONE);
    }

    public void setData(NativeAd ads) {
        mNativeAds = ads;
        if (mNativeAds != null) {
            adsSponsorType.setText(ads.getAdTitle());
            adsSponsorName.setText(ads.getAdSubtitle());
            adsTitle.setText(ads.getAdSubtitle());
            adsInstall.setText(ads.getAdCallToAction());
            try {
                Glide.with(mContext)
                    .load(ads.getAdCoverImage() != null ? ads.getAdCoverImage().getUrl() : ads.getAdIcon().getUrl())
                    .into(adsCover);
                if (ads.getAdChoicesIcon() != null) {
                    Glide.with(mContext)
                        .load(ads.getAdChoicesIcon().getUrl())
                        .into(adsAdchoice);
                }
                if (ads.getAdIcon() != null) {
                    Glide.with(mContext)
                        .load(ads.getAdIcon().getUrl())
                        .into(adsIcon);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(adsTitle);
            clickableViews.add(adsInstall);
            clickableViews.add(adsRoot);
            ads.unregisterView();
            ads.registerViewForInteraction(adsRoot, clickableViews);
            adsRoot.setVisibility(View.VISIBLE);
            setVisibility(View.VISIBLE);
        }

    }
}
