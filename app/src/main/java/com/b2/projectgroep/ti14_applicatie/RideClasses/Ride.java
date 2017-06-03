package com.b2.projectgroep.ti14_applicatie.RideClasses;


import com.b2.projectgroep.ti14_applicatie.R;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by harm on 18-5-2017.
 */

public class Ride implements Serializable {
    private int name;
    private int catogoryRide;
    private int rideImage;
    private int information;
    private String time = "";

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

    public void setTime(String s) {this.time = s;}

    @Override
    public String toString() {
        return "Ride{" +
                "name=" + name +
                ", catogoryRide=" + catogoryRide +
                ", rideImage=" + rideImage +
                ", information=" + information +
                ", time='" + time + '\'' +
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

    public static Ride getRideFromName(int name) {
        Log.i("Message", name + "   " + R.string.testRide_python_title);
        ArrayList<Ride> rides = getTestRides();
        for (int x = 0; x < rides.size(); x++) {
            if(rides.get(x).getName() == name) {
                Log.i("Message", "ride found");
                return rides.get(x);
            }
        }

        Log.i("Message", "ride not found");

        return null;
    }
}
