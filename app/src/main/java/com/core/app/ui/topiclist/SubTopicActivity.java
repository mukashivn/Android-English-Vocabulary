package com.core.app.ui.topiclist;

import android.content.Intent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.core.app.data.model.SubTopicModel;
import com.core.app.ui.adapter.SubTopicAdapter;
import com.core.app.ui.base.BaseActivity;
import com.core.app.ui.inter.ISubTopicListener;
import com.core.app.utils.AppConstants;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubTopicActivity extends BaseActivity implements ISubTopicView, ISubTopicListener {
    @BindView(R.id.topic_title)
    TextView topicTitle;
    @BindView(R.id.subtopic_title)
    TextView subtopicTitle;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    private SubTopicAdapter mSubTopicAdapter;

    @Inject
    ISubTopicPresenter<ISubTopicView> mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_topic_list;
    }

    @Override
    public void injectComponent() {
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);
    }

    @Override
    protected void init() {
        recycleview.setLayoutManager(new GridLayoutManager(this,2));
        mPresenter.init(this, getIntent().getIntExtra(AppConstants.EXTRA_TOPIC_ID,-1), getIntent().getIntExtra(AppConstants.EXTRA_COURSE_TYPE,-1));
        mPresenter.loadSubTopic();
    }

    @Override
    protected void loadAds() {

    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {

    }

    @OnClick(R.id.action_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onSubTopicClick(SubTopicModel subTopic) {
        mPresenter.handleNextScreen(subTopic);
    }

    @Override
    public void showTitle(String topicName, String lang) {
        topicTitle.setText(topicName);
        subtopicTitle.setText(lang);
        subtopicTitle.setVisibility(TextUtils.isEmpty(lang) ? View.GONE: View.VISIBLE);
    }

    @Override
    public void showSubTopicList(ArrayList<SubTopicModel> subTopics, String lang, int courseType) {
        mSubTopicAdapter = new SubTopicAdapter(this,subTopics,this,lang, courseType);
        recycleview.setAdapter(mSubTopicAdapter);
    }

    @Override
    public void showSubTopicDetail(String topicTitle, int subId, Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(AppConstants.EXTRA_TOPIC_TITLE,topicTitle);
        intent.putExtra(AppConstants.EXTRA_SUBTOPIC_ID,subId);
        startActivity(intent);
    }


}
