package com.b2.projectgroep.ti14_applicatie.RideClasses;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by dionb on 5-6-2017.
 */

public class PersonalActivity implements Serializable, Comparable<PersonalActivity> {
    String time;
    Ride ride;

    public PersonalActivity(String time, Ride ride) {
        this.time = time;
        this.ride = ride;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    @Override
    public int compareTo(@NonNull PersonalActivity o) {
        int hoursOne = Integer.valueOf(this.getTime().substring(0, 2));
        int minutesOne = Integer.valueOf(this.getTime().substring(3));

        int hoursTwo = Integer.valueOf(o.getTime().substring(0, 2));
        int minutesTwo = Integer.valueOf(o.getTime().substring(3));

        if(hoursOne < hoursTwo) {
            return -1;
        } else if(hoursOne == hoursTwo && minutesOne < minutesTwo) {
            return -1;
        } else {
            return 1;
        }
    }
}
