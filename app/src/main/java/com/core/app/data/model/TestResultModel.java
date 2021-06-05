package com.core.app.data.model;

import com.google.gson.annotations.SerializedName;

public class TestResultModel {
    @SerializedName("test1")
    int test1;
    @SerializedName("test2")
    int test2;
    @SerializedName("writing")
    int writing;
    @SerializedName("listen1")
    int listen1;
    @SerializedName("listen2")
    int listen2;
    @SerializedName("speak")
    int speak;

    public TestResultModel(int test1, int test2, int writing, int listen1, int listen2, int speak) {
        this.test1 = test1;
        this.test2 = test2;
        this.writing = writing;
        this.listen1 = listen1;
        this.listen2 = listen2;
        this.speak = speak;
    }

    public int getTest1() {
        return test1;
    }

    public int getTest2() {
        return test2;
    }

    public int getWriting() {
        return writing;
    }

    public int getListen1() {
        return listen1;
    }

    public int getListen2() {
        return listen2;
    }

    public int getSpeak() {
        return speak;
    }

    public void setTest1(int test1) {
        this.test1 = test1;
    }

    public void setTest2(int test2) {
        this.test2 = test2;
    }

    public void setWriting(int writing) {
        this.writing = writing;
    }

    public void setListen1(int listen1) {
        this.listen1 = listen1;
    }

    public void setListen2(int listen2) {
        this.listen2 = listen2;
    }

    public void setSpeak(int speak) {
        this.speak = speak;
    }
}
