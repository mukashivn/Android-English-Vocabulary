package com.core.app.utils;

import android.content.Context;

import com.core.ssvapp.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdsUtils {
    private static InterstitialAd sInterstitialAd;

    public static void showFullAds(Context context) {
        if (sInterstitialAd != null && sInterstitialAd.isLoaded()) {
            sInterstitialAd.show();
        }

    }

    public static void loadFullScreenAdvertising(Context context) {
        try {
            if (sInterstitialAd == null) {
                sInterstitialAd = new com.google.android.gms.ads.InterstitialAd(context);
                sInterstitialAd.setAdUnitId(context.getString(R.string.admodFullAds));
                // Load Ad
                if (sInterstitialAd != null) {
                    sInterstitialAd.loadAd(new AdRequest.Builder().build());
                    sInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                        }

                        @Override
                        public void onAdFailedToLoad(int i) {
                            super.onAdFailedToLoad(i);
                        }

                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            if (sInterstitialAd != null) {
                                sInterstitialAd.loadAd(new AdRequest.Builder().build());
                            }
                        }
                    });
                }

            } else {
                if (sInterstitialAd.isLoaded() && sInterstitialAd != null) {
                    sInterstitialAd.show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
