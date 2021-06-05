package com.core.app.ui.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.core.ssvapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.thumbnail)
    public ImageView thumbnail;
    @BindView(R.id.en_title)
    public TextView enTitle;
    @BindView(R.id.reg_title)
    public TextView regTitle;
    @BindView(R.id.item_root)
    public RelativeLayout itemRoot;

    public TopicHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
