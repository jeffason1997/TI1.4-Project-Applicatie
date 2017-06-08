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

/**
 * Created by tmbro on 8-6-2017.
 */

public class DiplomaAdapter extends ArrayAdapter<Ride>{
    public DiplomaAdapter(Context context, ArrayList<Ride> rides) {
        super(context, 0, rides);
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
        //timesVisited.setText();

        ImageView imaImageRide = (ImageView) convertView.findViewById(R.id.rideImage_diploma_image);
        imaImageRide.setImageResource(ride.getRideImage());

        return convertView;
    }
}
