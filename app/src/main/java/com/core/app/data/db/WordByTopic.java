package com.core.app.data.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Package: com.core.ssvapp.data.db
 * Created by: CuongCK
 * Date: 3/15/18
 */

public class WordByTopic extends RealmObject {
    public String chin;
    public String desc;
    public String esp;
    public String fav;
    public String fra;
    public String ger;
    public String hin;
    public String imageResourceId;
    public String ita;
    public String jap;
    public String kor;
    public String name;
    public String none;
    public String por;
    public String rus;
    public String sound;
    public String trans;
    public String tur;
    public String usage;
    @PrimaryKey
    public int id;
    public int subTopicId;

    public WordByTopic(){

    }

    public String getChin() {
        return chin;
    }

    public String getDesc() {
        return desc;
    }

    public String getEsp() {
        return esp;
    }

    public String getFav() {
        return fav;
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

    public String getSound() {
        return sound;
    }

    public String getTrans() {
        return trans;
    }

    public String getTur() {
        return tur;
    }

    public String getUsage() {
        return usage;
    }

    public int getId() {
        return id;
    }

    public int getSubTopicId() {
        return subTopicId;
    }
}
