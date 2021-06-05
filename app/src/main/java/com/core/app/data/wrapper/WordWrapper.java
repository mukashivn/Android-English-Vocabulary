package com.core.app.data.wrapper;

import com.core.app.data.db.WordByTopic;
import com.facebook.ads.NativeAd;

public class WordWrapper {
    private NativeAd nativeAd;
    private WordByTopic word;
    private boolean isContent;

    public WordWrapper(NativeAd nativeAd, WordByTopic word, boolean isContent) {
        this.nativeAd = nativeAd;
        this.word = word;
        this.isContent = isContent;
    }

    public NativeAd getNativeAd() {
        return nativeAd;
    }

    public WordByTopic getWord() {
        return word;
    }

    public boolean isContent() {
        return isContent;
    }

    public void setNativeAd(NativeAd nativeAd) {
        this.nativeAd = nativeAd;
    }
}
