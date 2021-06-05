package com.core.app.data.model;

import android.text.TextUtils;

import com.google.gson.Gson;

public class SubTopicModel {
    private String chin;
    private String esp;
    private String fra;
    private String ger;
    private String hin;
    private String imageResourceId;
    private String ita;
    private String jap;
    private String kor;
    private String name;
    private String none;
    private String por;
    private String rus;
    private String tur;
    private int id;
    private int topicId;

    private int test1_result;
    private int test2_result;
    private int writing_result;
    private int speaking_result;
    private int listening1_result;
    private int listening2_result;

    public SubTopicModel(String chin, String esp, String fra, String ger, String hin, String imageResourceId, String ita, String jap, String kor, String name, String none, String por, String rus, String tur, int id, int topicId) {
        this.chin = chin;
        this.esp = esp;
        this.fra = fra;
        this.ger = ger;
        this.hin = hin;
        this.imageResourceId = imageResourceId;
        this.ita = ita;
        this.jap = jap;
        this.kor = kor;
        this.name = name;
        this.none = none;
        this.por = por;
        this.rus = rus;
        this.tur = tur;
        this.id = id;
        this.topicId = topicId;
        if(this.none != null && !TextUtils.isEmpty(this.none)) {
            Gson gson = new Gson();
            TestResultModel resultModel = gson.fromJson(this.none, TestResultModel.class);
            this.test1_result = resultModel.test1;
            this.test2_result = resultModel.test2;
            this.writing_result = resultModel.writing;
            this.speaking_result = resultModel.speak;
            this.listening1_result = resultModel.listen1;
            this.listening2_result = resultModel.listen2;
        }
    }

    public String getChin() {
        return chin;
    }

    public String getEsp() {
        return esp;
    }

    public String getFra() {
        return fra;
    }

    public String getGer() {
        return ger;
    }

    public String getHin() {
        return hin;
    }

    public String getImageResourceId() {
        return imageResourceId;
    }

    public String getIta() {
        return ita;
    }

    public String getJap() {
        return jap;
    }

    public String getKor() {
        return kor;
    }

    public String getName() {
        return name;
    }

    public String getNone() {
        return none;
    }

    public String getPor() {
        return por;
    }

    public String getRus() {
        return rus;
    }

    public String getTur() {
        return tur;
    }

    public int getId() {
        return id;
    }

    public int getTopicId() {
        return topicId;
    }

    public int getTest1_result() {
        return test1_result;
    }

    public int getTest2_result() {
        return test2_result;
    }

    public int getWriting_result() {
        return writing_result;
    }

    public int getSpeaking_result() {
        return speaking_result;
    }

    public int getListening1_result() {
        return listening1_result;
    }

    public int getListening2_result() {
        return listening2_result;
    }
}
