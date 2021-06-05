package com.core.app.data.db;

import com.core.app.data.model.SubTopicModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Package: com.core.ssvapp.data.db
 * Created by: CuongCK
 * Date: 3/15/18
 */

public class SubTopic extends RealmObject {
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
    @PrimaryKey
    private int id;
    private int topicId;

    public SubTopic() {

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

    public void setNone(String none) {
        this.none = none;
    }

    public SubTopicModel map2Model() {
        SubTopicModel model = new SubTopicModel(chin, esp, fra, ger, hin, imageResourceId, ita, jap, kor, name, none, por, rus, tur, id, topicId);
        return model;
    }
}
