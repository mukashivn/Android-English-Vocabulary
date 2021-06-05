package com.core.app.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.core.app.data.db.Topic;
import com.core.app.data.wrapper.TopicWrapper;
import com.core.app.ui.inter.ITopicListener;
import com.core.app.ui.viewholder.AdsHolder;
import com.core.app.ui.viewholder.EmptyHolder;
import com.core.app.ui.viewholder.TopicHolder;
import com.core.app.utils.CommonUtils;
import com.core.app.utils.EnglisjViewUtils;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<TopicWrapper> mTopics;
    private ITopicListener mCallBack;
    private int CONTENT_TYPE = 0;
    private int ADS_TYPE = 1;
    private int EMPTY_TYPE = 2;
    private String mCurrentLang;

    public TopicAdapter(Context mContext, ArrayList<TopicWrapper> mTopics, ITopicListener mCallBack, String mCurrentLang) {
        this.mContext = mContext;
        this.mTopics = mTopics;
        this.mCallBack = mCallBack;
        this.mCurrentLang = mCurrentLang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == CONTENT_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_topic, parent, false);
            return new TopicHolder(view);

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
            TopicHolder holder = (TopicHolder) baseholder;
            Topic topic = mTopics.get(position).getTopic();
            try{
                Picasso.get()
                    .load(EnglisjViewUtils.getDrawableIDFromName(mContext, topic.getIcon()))
                    .into(holder.thumbnail);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }
            holder.enTitle.setText(topic.getName());
            holder.regTitle.setText(CommonUtils.getTopicLang(mCurrentLang, topic));
            holder.itemRoot.setOnClickListener(v -> {
                if (mCallBack != null) {
                    mCallBack.onTopicClick(topic);
                }
            });

        } else if (type == ADS_TYPE) {
            AdsHolder holder = (AdsHolder) baseholder;
            NativeAd ads = mTopics.get(position).getNativeAd();

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
    public int getItemViewType(int position) {
        TopicWrapper item = mTopics.get(position);
        if (item.isContent())
            return CONTENT_TYPE;
        else if (!item.isContent() && item.getNativeAd() == null)
            return EMPTY_TYPE;
        return ADS_TYPE;
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    public void setCurrentLang(String lang) {
        mCurrentLang = lang;
        notifyDataSetChanged();
    }


    public void addNativeAd(NativeAd nativeAd) {
        for (int i = 0; i < mTopics.size(); i++) {
            TopicWrapper item = mTopics.get(i);
            if (!item.isContent() && item.getNativeAd() == null) {
                mTopics.get(i).setNativeAd(nativeAd);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void updateData(ArrayList<TopicWrapper> topics) {
        mTopics.clear();
        mTopics.addAll(topics);
        notifyDataSetChanged();
    }
}
