package com.core.app.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.core.app.ui.base.BaseActivity;
import com.core.app.ui.base.BaseFragment;
import com.core.app.ui.custom.DialogRateMe;
import com.core.app.ui.custom.ItemPopup;
import com.core.app.ui.home.HomeFragment;
import com.core.app.ui.store.StoreActivity;
import com.core.app.utils.AppConstants;
import com.core.ssvapp.BuildConfig;
import com.core.ssvapp.R;
import com.facebook.ads.NativeAd;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainView, DialogRateMe.OnDialogRateCB, NavigationView.OnNavigationItemSelectedListener {
    @Inject
    IMainPresenter<IMainView> mPresenter;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.action_switch_lang)
    ImageButton mActionFlag;

    private DialogRateMe mRateMeDialog;
    private boolean doubleBackToExitPressedOnce;
    private PopupWindow mChangeLangPopUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void injectComponent() {
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        setUnBinder(ButterKnife.bind(this));
    }

    @Override
    protected void init() {
        navView.setNavigationItemSelectedListener(this);
        mPresenter.init(this);
        replaceFragment(HomeFragment.newInstance(), HomeFragment.class.getSimpleName(), R.id.container);

        mRateMeDialog = new DialogRateMe(this);
        mRateMeDialog.setCallBack(this);

        mPresenter.showFullAds(this);
    }

    @Override
    protected void loadAds() {
        //TODO: Load ads here. Choice native ads or banner ads


    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        mPresenter.handleExitApp();
    }

    @Override
    public void showNativeAds(NativeAd nativeAd) {

    }

    @OnClick({R.id.drawer_menu_iv, R.id.action_shop, R.id.action_switch_lang, R.id.action_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.drawer_menu_iv: {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
            break;
            case R.id.action_shop:
                startActivityForResult(new Intent(this, StoreActivity.class), AppConstants.REQUEST_PURCHASE);
                break;
            case R.id.action_switch_lang:
                showPopup(view);
                break;
            case R.id.action_search:
                break;
        }
    }

    private void showPopup(View view) {
        int[] location = new int[2];
        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        view.getLocationOnScreen(location);
        //Initialize the Point with x, and y positions
        Point point = new Point();
        point.x = location[0];
        point.y = location[1];

        createPopUp(point);
    }

    private void createPopUp(Point point) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.lang_popup, null);

        mChangeLangPopUp = new PopupWindow(this);
        mChangeLangPopUp.setContentView(layout);
        mChangeLangPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        mChangeLangPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mChangeLangPopUp.setFocusable(true);

        int OFFSET_X = -130;
        int OFFSET_Y = 150;
        //Clear the default translucent background
        mChangeLangPopUp.setBackgroundDrawable(new BitmapDrawable());
        mChangeLangPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, point.x + OFFSET_X, point.y + OFFSET_Y);

        ItemPopup langEn = (ItemPopup) layout.findViewById(R.id.lang_eng);
        langEn.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_EN);
        });

        ItemPopup ger = (ItemPopup) layout.findViewById(R.id.lang_ger);
        ger.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_GER);
        });

        ItemPopup fra = (ItemPopup) layout.findViewById(R.id.lang_fra);
        fra.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_FRA);
        });

        ItemPopup rus = (ItemPopup) layout.findViewById(R.id.lang_rus);
        rus.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_RUS);
        });

        ItemPopup spa = (ItemPopup) layout.findViewById(R.id.lang_spa);
        spa.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_ESP);
        });

        ItemPopup tur = (ItemPopup) layout.findViewById(R.id.lang_tur);
        tur.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_TUR);
        });

        ItemPopup sau = (ItemPopup) layout.findViewById(R.id.lang_sau);
        sau.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_ITA);
        });

        ItemPopup sou = (ItemPopup) layout.findViewById(R.id.lang_sou);
        sou.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_KOR);
        });

        ItemPopup chi = (ItemPopup) layout.findViewById(R.id.lang_chi);
        chi.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_CHI);
        });

        ItemPopup jap = (ItemPopup) layout.findViewById(R.id.lang_jap);
        jap.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_JAP);
        });

        ItemPopup ind = (ItemPopup) layout.findViewById(R.id.lang_ind);
        ind.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_HIN);
        });


        ItemPopup bra = (ItemPopup) layout.findViewById(R.id.lang_bra);
        bra.setOnClickListener(v -> {
            mChangeLangPopUp.dismiss();
            mPresenter.changeLang(AppConstants.LANG_POR);
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_rate: {
                mPresenter.rateMe(false);
            }
            break;
            case R.id.nav_store: {
                startActivityForResult(new Intent(this, StoreActivity.class), AppConstants.REQUEST_PURCHASE);
            }
            break;
            case R.id.nav_feedback:
                break;
            case R.id.nav_share:
                shareApp();
                break;
            case R.id.nav_about_us:
                break;
            default:
                break;
        }
        closeNavigationView();
        return true;
    }

    private void shareApp() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + " \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private void closeNavigationView() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void updateFlag(int currentFlag) {
        mActionFlag.setImageResource(currentFlag);
    }

    @Override
    public void updateLang(String lang) {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        if (homeFragment != null) {
            homeFragment.updateLang(lang);
        }
    }

    @Override
    public void showDialogAskRate(boolean onExit) {
        mRateMeDialog.show(onExit);
    }

    @Override
    public void exitApp() {
        finish();
    }

    @Override
    public void openAppInStore(String applicationId) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + applicationId)));
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + applicationId)));
        }
    }

    @Override
    public void showAskDialog() {
        mRateMeDialog.show(true);
    }

    @Override
    public void exitAppNow() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.double_click_to_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    public void onNoRate(boolean dontAgain, boolean onExit) {
        mPresenter.onRateComplete(onExit, dontAgain, false);
    }

    @Override
    public void onRate(boolean onExit) {
        mPresenter.onRateComplete(onExit, false, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUEST_PURCHASE) {
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
