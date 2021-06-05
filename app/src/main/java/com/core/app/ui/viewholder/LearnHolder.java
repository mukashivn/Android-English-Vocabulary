package com.core.app.ui.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.ssvapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearnHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.thumbnail)
    public ImageView thumbnail;
    @BindView(R.id.word_lang)
    public TextView wordLang;
    @BindView(R.id.word_name)
    public TextView wordName;
    @BindView(R.id.item_root)
    public FrameLayout itemRoot;
    public LearnHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
