package com.core.app.ui.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.core.app.ApplicationImpl;
import com.core.app.di.component.ActivityComponent;
import com.core.app.di.component.DaggerActivityComponent;
import com.core.app.di.module.ActivityModule;
import com.core.app.utils.AdsUtils;
import com.core.app.utils.CommonUtils;
import com.core.app.utils.NetworkUtils;
import com.core.ssvapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.irozon.library.HideKey;

import java.util.Objects;

import butterknife.Unbinder;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created by CuongCK on 2/13/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements IView, BaseFragment.Callback {

    private ProgressDialog progressDialog;

    private ActivityComponent mActivityComponent;

    private Unbinder mUnBinder;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((ApplicationImpl) getApplication()).getComponent())
                .build();
        autoHideKeyBoard();
        hideKeyboard();
        initFireBaseAnalytics();
        injectComponent();
        init();
        loadAds();
    }

    public void logEvent(String event, Bundle bundle) {
        mFirebaseAnalytics.logEvent(event, bundle);
    }

    private void initFireBaseAnalytics() {
        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    public abstract int getLayoutId();

    public abstract void injectComponent();

    protected abstract void init();

    protected abstract void loadAds();

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showFullAds() {
        AdsUtils.showFullAds(this);
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.content), message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    public void showExitTest() {
        Dialog exitDialog = new Dialog(this, R.style.Theme_Dialog);
        exitDialog.setContentView(R.layout.layout_confirm_exit_test);

        Button noCmd = (Button) exitDialog.findViewById(R.id.cmd_no);
        noCmd.setOnClickListener(v -> {
            exitDialog.dismiss();
        });

        Button yesCmd = (Button) exitDialog.findViewById(R.id.cmd_yes);
        yesCmd.setOnClickListener(v -> {
            exitDialog.dismiss();
            finish();
        });


        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.show();
    }

    public void showResultTest(int correctCount, int wrongCount) {
        Dialog exitDialog = new Dialog(this, R.style.Theme_Dialog);
        exitDialog.setContentView(R.layout.layout_test_result);

        TextView commnetView = (TextView) exitDialog.findViewById(R.id.test_comment);
        TextView testResultView = (TextView) exitDialog.findViewById(R.id.test_result);

        String correctStr = correctCount + " " + "Correct";
        String and = " and ";
        String wrongStr = wrongCount + " " + "Wrong";

        Spannable wordtoSpan = new SpannableString(correctStr + and + wrongStr);

        wordtoSpan.setSpan(new ForegroundColorSpan(Color.GREEN), 0, correctStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), correctStr.length() + and.length(), correctStr.length() + and.length() + wrongStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        testResultView.setText(wordtoSpan);
        commnetView.setText(CommonUtils.getCommnet(this, correctCount, wrongCount));

        Button noCmd = (Button) exitDialog.findViewById(R.id.cmd_test_again);
        noCmd.setOnClickListener(v -> {
            exitDialog.dismiss();
            testAgain();
        });

        Button yesCmd = (Button) exitDialog.findViewById(R.id.cmd_exit);
        yesCmd.setOnClickListener(v -> {
            exitDialog.dismiss();
            finish();
        });

        exitDialog.setCancelable(false);
        exitDialog.setCanceledOnTouchOutside(false);

        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.show();
    }

    protected void testAgain() {

    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void autoHideKeyBoard() {
        HideKey.initialize(this);
    }

    @Override
    public void openActivityOnTokenExpire() {
        finish();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    public void addFragment(Fragment fragment, String TAG, int containerId) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(containerId, fragment, TAG)
                .addToBackStack(TAG)
                .commit();
    }

    public void replaceFragment(Fragment fragment, String TAG, int containerId) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(containerId, fragment, TAG)
                .commit();
    }
}
