package com.b2.projectgroep.ti14_applicatie.RideClasses;


import com.b2.projectgroep.ti14_applicatie.R;


import java.io.Serializable;
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
        toReturn.put("Black Mamba", new Ride(R.string.testRide_black_mamba_title, R.string.testRide_black_mamba_type, R.string.testRide_black_mamba_information, R.drawable.black_mamba));
        toReturn.put("Dreamtown", new Ride(R.string.testRide_dreamtown_title, R.string.testRide_dreamtown_type, R.string.testRide_dreamtown_information, R.drawable.draaimolen_nostalgisch));
        toReturn.put("Fire Machine", new Ride(R.string.testRide_fire_machine_title, R.string.testRide_fire_machine_type, R.string.testRide_fire_machine_information, R.drawable.fire_machine));
        toReturn.put("Spirit Realm", new Ride(R.string.testRide_spirit_realm_title, R.string.testRide_spirit_realm_type, R.string.testRide_spirit_realm_information, R.drawable.spirit_realm));
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
        if(ride.getName() == R.string.testRide_black_mamba_title) {
            return "Black Mamba";
        } else if(ride.getName() == R.string.testRide_dreamtown_title) {
            return "Dreamtown";
        } else if(ride.getName() == R.string.testRide_fire_machine_title) {
            return "Fire Machine";
        } else if(ride.getName() == R.string.testRide_spirit_realm_title) {
            return "Spirit Realm";
        }

        return "";
    }
}
