package com.core.app.ui.fragement_demo;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.app.ui.base.BaseFragment;
import com.core.app.ui.custom.NativeAdsView;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;
;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DemoFragment extends BaseFragment implements IDemoView {
    @Inject
    IDemoPresenter<IDemoView> mPresenter;
    @BindView(R.id.nativeAdsView)
    NativeAdsView nativeAdsView;
    Unbinder unbinder;

    public static DemoFragment newInstance() {

        Bundle args = new Bundle();

        DemoFragment fragment = new DemoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, inflater.inflate(getLayoutId(), container, false));
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_demo;
    }

    @Override
    protected void injectView(View view) {
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this, view));
        mPresenter.onAttach(this);
    }

    @Override
    protected void init() {
        //Init everything you want
    }

    @Override
    protected void loadAds() {
        //TODO: Load Native or Banner
        mPresenter.loadNativeAds(getBaseActivity());
    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {
        Handler mainHandler = new Handler(getBaseActivity().getMainLooper());
        // This is your code
        Runnable myRunnable = () -> nativeAdsView.setData(nativeAd);
        mainHandler.post(myRunnable);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
        unbinder.unbind();
    }
}
