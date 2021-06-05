package com.core.app.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.core.app.data.db.WordByTopic;
import com.core.app.data.wrapper.WordWrapper;
import com.core.app.ui.inter.ICommonListener;
import com.core.app.ui.viewholder.AdsHolder;
import com.core.app.ui.viewholder.EmptyHolder;
import com.core.app.ui.viewholder.LearnHolder;
import com.core.app.utils.CommonUtils;
import com.core.app.utils.EnglisjViewUtils;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LearnAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<WordWrapper> mWords;
    private ICommonListener mCallBack;
    private String mLang;

    private int CONTENT_TYPE = 0;
    private int ADS_TYPE = 1;
    private int EMPTY_TYPE = 2;

    public LearnAdapter(Context mContext, ArrayList<WordWrapper> mWords, ICommonListener mCallBack, String mLang) {
        this.mContext = mContext;
        this.mWords = mWords;
        this.mCallBack = mCallBack;
        this.mLang = mLang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == CONTENT_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn, parent, false);
            return new LearnHolder(view);

        } else if (viewType == ADS_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_native_ads, parent, false);
            return new AdsHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder baseholder, int position) {
        int type = getItemViewType(position);
        if (type == CONTENT_TYPE) {
            LearnHolder holder = (LearnHolder) baseholder;
            WordByTopic topic = mWords.get(position).getWord();
            try {
                Picasso.get()
                    .load(EnglisjViewUtils.getDrawableIDFromName(mContext, topic.getImageResourceId()))
                    .into(holder.thumbnail);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            holder.wordName.setText(topic.getName());
            holder.wordLang.setText(CommonUtils.getWordLang(mLang, topic));
            holder.itemRoot.setOnClickListener(v -> {
                if (mCallBack != null) {
                    mCallBack.onWordClick(topic);
                }
            });

        } else if (type == ADS_TYPE) {
            AdsHolder holder = (AdsHolder) baseholder;
            NativeAd ads = mWords.get(position).getNativeAd();

            if (ads == null) {
                holder.adsRoot.setVisibility(View.GONE);
            } else {
                holder.adsSponsorType.setText(ads.getAdTitle());
                holder.adsSponsorName.setText(ads.getAdSubtitle());
                holder.adsTitle.setText(ads.getAdSubtitle());
                holder.adsInstall.setText(ads.getAdCallToAction());
                try {
                    Glide.with(mContext)
                        .load(ads.getAdCoverImage() != null ? ads.getAdCoverImage().getUrl() : ads.getAdIcon().getUrl())
                        .into(holder.adsCover);
                    if (ads.getAdChoicesIcon() != null) {
                        Glide.with(mContext)
                            .load(ads.getAdChoicesIcon().getUrl())
                            .into(holder.adsAdchoice);
                    }
                    if (ads.getAdIcon() != null) {
                        Glide.with(mContext)
                            .load(ads.getAdIcon().getUrl())
                            .into(holder.adsIcon);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(holder.adsTitle);
                clickableViews.add(holder.adsInstall);
                clickableViews.add(holder.adsRoot);
                ads.unregisterView();
                ads.registerViewForInteraction(holder.adsRoot, clickableViews);
                holder.adsRoot.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    @Override
    public int getItemViewType(int position) {
        WordWrapper item = mWords.get(position);
        if (item.isContent())
            return CONTENT_TYPE;
        else if (!item.isContent() && item.getNativeAd() == null)
            return EMPTY_TYPE;
        return ADS_TYPE;
    }

    public void addNativeAd(NativeAd nativeAd) {
        for (int i = 0; i < mWords.size(); i++) {
            WordWrapper item = mWords.get(i);
            if (!item.isContent() && item.getNativeAd() == null) {
                mWords.get(i).setNativeAd(nativeAd);
                notifyItemChanged(i);
                break;
            }
        }
    }
}
