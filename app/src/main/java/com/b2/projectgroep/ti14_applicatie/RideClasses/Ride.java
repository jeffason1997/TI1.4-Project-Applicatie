package com.b2.projectgroep.ti14_applicatie.RideClasses;


import com.b2.projectgroep.ti14_applicatie.R;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by harm on 18-5-2017.
 */

public class Ride implements Serializable {
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

    public int getInformation() {
        return information;
    }

    public void setInformation(int information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "name=" + name +
                ", catogoryRide=" + catogoryRide +
                ", rideImage=" + rideImage +
                ", information=" + information +
                '}';
    }

    public static HashMap<String, Ride> getTestRides() {
        HashMap<String, Ride> toReturn = new HashMap<>();
        toReturn.put("Python", new Ride(R.string.testRide_python_title, R.string.testRide_python_type, R.string.testRide_python_information, R.drawable.python));
        toReturn.put("Draaimolen", new Ride(R.string.testRide_carousel_title, R.string.testRide_carousel_type, R.string.testRide_carousel_information, R.drawable.draaimolen_nostalgisch));
        toReturn.put("Houten Achtbaan", new Ride(R.string.testRide_wooden_coaster_title, R.string.testRide_wooden_coaster_type, R.string.testRide_wooden_coaster_information, R.drawable.draaimolen_nostalgisch));
        toReturn.put("Lange Jan", new Ride(R.string.testRide_langejan_title, R.string.testRide_lange_jan_type, R.string.testRide_lange_jan_information, R.drawable.draaimolen_nostalgisch));
        return toReturn;
    }

    public static Ride getRideFromName(String name) {
        HashMap<String, Ride> rides = getTestRides();
        if(rides.containsKey(name)) {
            return rides.get(name);
        } else {
            return null;
        }
    }

    public static String getKeyFromRide(Ride ride) {
        if(ride.getName() == R.string.testRide_python_title) {
            return "Python";
        } else if(ride.getName() == R.string.testRide_carousel_title) {
            return "Draaimolen";
        } else if(ride.getName() == R.string.testRide_wooden_coaster_title) {
            return "Houten Achtbaan";
        } else if(ride.getName() == R.string.testRide_langejan_title) {
            return "Lange Jan";
        }

        return "";
    }
}
