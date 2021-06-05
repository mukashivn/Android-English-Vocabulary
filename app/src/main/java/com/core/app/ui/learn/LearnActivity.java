package com.core.app.ui.learn;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.core.app.data.db.WordByTopic;
import com.core.app.data.wrapper.WordWrapper;
import com.core.app.ui.adapter.LearnAdapter;
import com.core.app.ui.base.BaseActivity;
import com.core.app.ui.custom.LearnDialog;
import com.core.app.ui.inter.ICommonListener;
import com.core.app.utils.AppConstants;
import com.core.app.utils.EnglisjViewUtils;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LearnActivity extends BaseActivity implements ILearnView, ICommonListener, LearnDialog.ILearnListener {
    @Inject
    ILearnPresenter<ILearnView> mPresenter;
    @BindView(R.id.topic_title)
    TextView topicTitle;
    @BindView(R.id.subtopic_title)
    TextView subtopicTitle;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    private LearnAdapter mLearnAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_learn;
    }

    @Override
    public void injectComponent() {
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);
    }

    @Override
    protected void init() {
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.init(this,getIntent().getStringExtra(AppConstants.EXTRA_TOPIC_TITLE),getIntent().getIntExtra(AppConstants.EXTRA_SUBTOPIC_ID,-1));
        mPresenter.loadData();
    }

    @Override
    protected void loadAds() {
        mPresenter.loadAds();
    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {
        if(mLearnAdapter != null){
            mLearnAdapter.addNativeAd(nativeAd);
        }
        mPresenter.loadAds();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onWordClick(WordByTopic word) {
        LearnDialog learnDialog = new LearnDialog(this, R.style.BottomDialogs);
        learnDialog.setCallBack(this);
        learnDialog.show(word);
    }

    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showTitle(String mainTopic, String subTopic) {
        topicTitle.setText(mainTopic);
        subtopicTitle.setText(subTopic);
        subtopicTitle.setVisibility(TextUtils.isEmpty(subTopic) ? View.GONE: View.VISIBLE);
    }

    @Override
    public void showWords(ArrayList<WordWrapper> wordWrappers, String lang) {
        mLearnAdapter = new LearnAdapter(this,wordWrappers,this,lang);
        recycleview.setAdapter(mLearnAdapter);
    }

    @Override
    public void onListening(boolean isSlow, String sound) {
        //Listening
        mPresenter.playSound(this, EnglisjViewUtils.getRawIDFromName(this,sound),isSlow);
    }
}
