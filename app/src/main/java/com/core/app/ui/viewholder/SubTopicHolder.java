package com.core.app.ui.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.core.ssvapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubTopicHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.thumbnail)
    public ImageView thumbnail;
    @BindView(R.id.subtopic_lang)
    public TextView subtopicLang;
    @BindView(R.id.subtopic_name)
    public TextView subtopicName;
    @BindView(R.id.root)
    public LinearLayout mRoot;
    @BindView(R.id.determinateBar)
    public ProgressBar mProgressBar;

    public SubTopicHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
