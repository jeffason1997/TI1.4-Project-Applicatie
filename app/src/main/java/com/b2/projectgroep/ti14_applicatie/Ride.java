package com.b2.projectgroep.ti14_applicatie;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by harm on 18-5-2017.
 */

public class Ride implements Serializable{
    private String name;
    private String catogoryRide;
    private int rideImage;

    public Ride(String name, String catogoryRide, int rideImage) {
        this.name = name;
        this.catogoryRide = catogoryRide;
        this.rideImage = rideImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatogoryRide() {
        return catogoryRide;
    }

    public void setCatogoryRide(String catogoryRide) {
        this.catogoryRide = catogoryRide;
    }

    public int getRideImage() {
        return rideImage;
    }

    public void setRideImage(int rideImage) {
        this.rideImage = rideImage;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "name='" + name + '\'' +
                ", catogoryRide='" + catogoryRide + '\'' +
                ", rideImage=" + rideImage +
                '}';
    }
}
