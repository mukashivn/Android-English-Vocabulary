package com.core.app.data.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Package: com.core.ssvapp.data.db
 * Created by: CuongCK
 * Date: 3/7/18
 */

public class Topic extends RealmObject {
    private String chin;
    private String cover;
    private String esp;
    private String fra;
    private String ger;
    private String hin;
    private String icon;
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

    public Topic() {
    }

    public Topic(int id, String chin, String cover, String esp, String fra, String ger, String hin, String icon, String imageResourceId, String ita, String jap, String kor, String name, String none, String por, String rus, String tur) {
        this.chin = chin;
        this.cover = cover;
        this.esp = esp;
        this.fra = fra;
        this.ger = ger;
        this.hin = hin;
        this.icon = icon;
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
    }

    public String getChin() {
        return chin;
    }

    public String getCover() {
        return cover;
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

    public String getIcon() {
        return icon;
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
}
