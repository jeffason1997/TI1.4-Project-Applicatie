package com.b2.projectgroep.ti14_applicatie.DiplomaClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tmbro on 8-6-2017.
 */

public class DiplomaAdapter extends ArrayAdapter<Ride>{

    ArrayList<PersonalActivity> activities;

    public DiplomaAdapter(Context context, ArrayList<Ride> rides,ArrayList<PersonalActivity> activities) {
        super(context, 0, rides);
        this.activities = activities;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Ride ride = getItem(position);

        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_row_diploma, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.diploma_rideNameTitle_tv);
        tvName.setText(ride.getName());

        TextView tvDescName = (TextView) convertView.findViewById(R.id.diploma_rideNameDesc_tv);
        tvDescName.setText(ride.getName());

        TextView timesVisited = (TextView) convertView.findViewById(R.id.diploma_rideTimes_tv);
        System.out.println(activities.toString());
        String boiy = ""+getAmount(ride.getName());
        timesVisited.setText(boiy);

        ImageView imaImageRide = (ImageView) convertView.findViewById(R.id.rideImage_diploma_image);
        imaImageRide.setImageResource(ride.getRideImage());

        return convertView;
    }

    public int getAmount(int name){
        int counter = 0;
        for(int i=0;i<activities.size();i++){
            if(activities.get(i).getRide().getName()==(name)){
                counter++;
            }
        }
        return counter;
    }
}
