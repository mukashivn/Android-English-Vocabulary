package com.core.app.ui.viewholder;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.ssvapp.R;
import com.santalu.aspectratioimageview.AspectRatioImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdsHolder extends RecyclerView.ViewHolder {
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

    public AdsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
