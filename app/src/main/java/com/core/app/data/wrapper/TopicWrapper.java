package com.core.app.data.wrapper;

import com.core.app.data.db.Topic;
import com.facebook.ads.NativeAd;

public class TopicWrapper {
    private Topic topic;
    private NativeAd nativeAd;
    private boolean isContent;

    public TopicWrapper(Topic topic, NativeAd nativeAd, boolean isContent) {
        this.topic = topic;
        this.nativeAd = nativeAd;
        this.isContent = isContent;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public NativeAd getNativeAd() {
        return nativeAd;
    }

    public void setNativeAd(NativeAd nativeAd) {
        this.nativeAd = nativeAd;
    }

    public boolean isContent() {
        return isContent;
    }

    public void setContent(boolean content) {
        isContent = content;
    }
}
