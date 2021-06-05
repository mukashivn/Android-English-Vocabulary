package com.core.app.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.ssvapp.R;


public class ItemPopup extends LinearLayout {
    private Context mContext;

    public ItemPopup(Context context) {
        super(context);
        init(context,null);
    }

    public ItemPopup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ItemPopup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemPopup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        this.mContext = context;
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PopupView, 0, 0);
        String menuText;
        Drawable icon;

        try {
            menuText = array.getString(R.styleable.PopupView_withText);
            icon = array.getDrawable(R.styleable.PopupView_withImage);
        } finally {
            array.recycle();
        }

        //Find View
        View rootView = inflate(getContext(), R.layout.item_popup, this);
        ImageView iconView = (ImageView) rootView.findViewById(R.id.flag);
        TextView menuTextView = (TextView) rootView.findViewById(R.id.lang);

        iconView.setImageDrawable(icon);
        menuTextView.setText(menuText);
    }
}
