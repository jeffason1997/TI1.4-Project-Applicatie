package com.b2.projectgroep.ti14_applicatie.CardClasses;

import android.support.annotation.NonNull;

/**
 * Created by Jeffrey on 12-6-2017.
 */

public class Card implements Comparable<Card> {
    private String name;
    private String surName;
    private String id;

    public Card(String name,String surName, String id) {
        this.name = name;
        this.surName = surName;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public int compareTo(@NonNull Card o) {
        int id1 = Integer.valueOf(this.getId());
        int id2 = Integer.valueOf(o.getId());


        if(id1 < id2) {
            return -1;
        } else {
            return 1;
        }
    }
}
