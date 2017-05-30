package com.b2.projectgroep.ti14_applicatie;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by harm on 18-5-2017.
 */

public class Ride implements Serializable{
    private int name;
    private int catogoryRide;
    private int rideImage;
    private int information;

    public Ride(int name, int catogoryRide, int information, int rideImage) {
        this.name = name;
        this.catogoryRide = catogoryRide;
        this.rideImage = rideImage;
        this.information = information;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getCatogoryRide() {
        return catogoryRide;
    }

    public void setCatogoryRide(int catogoryRide) {
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

    public static ArrayList<Ride> getTestRides() {
        ArrayList<Ride> rides = new ArrayList<>();
        rides.add(new Ride(R.string.testRide_python_title, R.string.testRide_python_type, R.string.testRide_python_information, R.drawable.python));
        rides.add(new Ride(R.string.testRide_carousel_title, R.string.testRide_carousel_type, R.string.testRide_carousel_information, R.drawable.draaimolen_nostalgisch));
        rides.add(new Ride(R.string.testRide_wooden_coaster_title, R.string.testRide_wooden_coaster_type, R.string.testRide_wooden_coaster_information, R.drawable.draaimolen_nostalgisch));
        rides.add(new Ride(R.string.testRide_langejan_title, R.string.testRide_lange_jan_type, R.string.testRide_lange_jan_information, R.drawable.draaimolen_nostalgisch));
        return rides;
    }
}
