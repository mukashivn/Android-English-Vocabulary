package com.core.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocosw.bottomsheet.BottomSheet;
import com.core.app.data.db.Topic;
import com.core.app.data.wrapper.TopicWrapper;
import com.core.app.ui.adapter.TopicAdapter;
import com.core.app.ui.base.BaseFragment;
import com.core.app.ui.inter.ITopicListener;
import com.core.app.ui.store.StoreActivity;
import com.core.app.ui.topiclist.SubTopicActivity;
import com.core.app.utils.AppConstants;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.Font;

public class HomeFragment extends BaseFragment implements IHomeView, ITopicListener {
    @Inject
    IHomePresenter<IHomeView> mPresenter;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private TopicAdapter mTopicAdapter;
    private BottomSheet mBottomSheet;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void injectView(View view) {
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this, view));
        mPresenter.onAttach(this);

    }

    @Override
    protected void init() {
        recycleview.setLayoutManager(new GridLayoutManager(getBaseActivity(), 2));
        mPresenter.init(getBaseActivity());
        mPresenter.loadTopic();
    }

    @Override
    protected void loadAds() {
        //mPresenter.loadAds();
    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {
        if (mTopicAdapter == null) {
            Handler handler = new Handler();
            handler.postDelayed(() -> {

            }, 1000);
        }
        if (mTopicAdapter != null) {
            mTopicAdapter.addNativeAd(nativeAd);
        }
        mPresenter.loadAds();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();

    }

    @Override
    public void onTopicClick(Topic topic) {
        //TODO: open Bottom Sheet
        mPresenter.openBottomSheet(topic);
    }

    @Override
    public void showTopic(ArrayList<TopicWrapper> topics, String lang) {
        if (mTopicAdapter == null) {
            mTopicAdapter = new TopicAdapter(getBaseActivity(), topics, this, lang);
            recycleview.setAdapter(mTopicAdapter);
        } else {
            mTopicAdapter.updateData(topics);
        }
    }

    @Override
    public void showBottomSheet() {
        if (mBottomSheet == null)
            mBottomSheet = new BottomSheet.Builder(getBaseActivity(), R.style.BottomSheet_StyleDialog).sheet(R.menu.bottom_sheet).listener((dialog, which) -> {
                if (which != R.id.bot_cancel)
                    mPresenter.handleShowSubTopic(which);
            }).grid().build();
        mBottomSheet.show();
    }

    @Override
    public void showSubTopic(int id, int courseType) {
        Intent intent = new Intent(getBaseActivity(), SubTopicActivity.class);
        intent.putExtra(AppConstants.EXTRA_TOPIC_ID, id);
        intent.putExtra(AppConstants.EXTRA_COURSE_TYPE, courseType);
        startActivity(intent);
    }

    public void updateLang(String lang) {
        mTopicAdapter.setCurrentLang(lang);
    }

    @Override
    public void showStore() {
        startActivityForResult(new Intent(getBaseActivity(), StoreActivity.class), AppConstants.REQUEST_PURCHASE);
    }

    @Override
    public void showGuestUpgradeDialog() {
        new AndExAlertDialog.Builder(getBaseActivity())
                .setTitle(getString(R.string.purchase_title))
                .setMessage(getString(R.string.purchase_message)).
                setPositiveBtnText(getString(R.string.cmd_purchase))
                .setNegativeBtnText(getString(R.string.cmd_showads))
                .setFont(Font.IRAN_SANS)
                .setImage(R.mipmap.ic_launcher, 20)
                .setCancelableOnTouchOutside(false)
                .OnPositiveClicked(input -> {
                    showStore();
                })
                .OnNegativeClicked(input -> {
                    showFullAds();
                })
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.REQUEST_PURCHASE) {
            mPresenter.reloadData();
        }
    }
}
