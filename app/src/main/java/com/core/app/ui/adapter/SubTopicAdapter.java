package com.core.app.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.app.data.model.SubTopicModel;
import com.core.app.ui.inter.ISubTopicListener;
import com.core.app.ui.viewholder.SubTopicHolder;
import com.core.app.utils.AppConstants;
import com.core.app.utils.CommonUtils;
import com.core.app.utils.EnglisjViewUtils;
import com.core.ssvapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubTopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<SubTopicModel> mSubTopics;
    private ISubTopicListener mCallBack;
    private String lang;
    private int courseType;

    public SubTopicAdapter(Context mContext, ArrayList<SubTopicModel> mSubTopics, ISubTopicListener mCallBack, String lang, int courseType) {
        this.mContext = mContext;
        this.mSubTopics = mSubTopics;
        this.mCallBack = mCallBack;
        this.lang = lang;
        this.courseType = courseType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_topic, parent, false);
        return new SubTopicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder baseholder, int position) {
        SubTopicModel subTopic = mSubTopics.get(position);
        SubTopicHolder holder = (SubTopicHolder) baseholder;
        try {
            Picasso.get()
                .load(EnglisjViewUtils.getDrawableIDFromName(mContext, subTopic.getImageResourceId()))
                .into(holder.thumbnail);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        holder.subtopicName.setText(subTopic.getName());
        holder.subtopicLang.setText(CommonUtils.getSubTopicLang(lang, subTopic));
        holder.subtopicLang.setVisibility(lang.equals(AppConstants.LANG_EN) ? View.GONE : View.VISIBLE);

        if(courseType == AppConstants.COURSE_LEARN || courseType == AppConstants.COURSE_FLASH){
            holder.mProgressBar.setVisibility(View.GONE);
        }else {
            holder.mProgressBar.setVisibility(View.VISIBLE);
            switch (courseType){
                case AppConstants.COURSE_TEST:
                    holder.mProgressBar.setProgress(subTopic.getTest1_result());
                break;
                case AppConstants.COURSE_TEST_2:
                    holder.mProgressBar.setProgress(subTopic.getTest2_result());
                    break;
                case AppConstants.COURSE_LISTENING:
                    holder.mProgressBar.setProgress(subTopic.getListening1_result());
                    break;
                case AppConstants.COURSE_LISTENING_2:
                    holder.mProgressBar.setProgress(subTopic.getListening2_result());
                    break;
                case AppConstants.COURSE_WRITING:
                    holder.mProgressBar.setProgress(subTopic.getWriting_result());
                    break;
                case AppConstants.COURSE_SPEAKING:
                    holder.mProgressBar.setProgress(subTopic.getSpeaking_result());
                    break;
            }
        }
        holder.mRoot.setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onSubTopicClick(mSubTopics.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSubTopics.size();
    }

}
