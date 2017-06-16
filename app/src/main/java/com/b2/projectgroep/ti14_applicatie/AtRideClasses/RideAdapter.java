package com.b2.projectgroep.ti14_applicatie.AtRideClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import java.util.ArrayList;

/**
 * Created by harm on 18-5-2017.
 */

public class RideAdapter extends ArrayAdapter<Ride> {
    public RideAdapter(Context context, ArrayList<Ride> rides) {
        super(context, 0, rides);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Ride ride = getItem(position);

        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_row, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.rideName_tv);
        tvName.setText(ride.getName());

        ImageView imaImageRide = (ImageView) convertView.findViewById(R.id.rideImage_image);
        imaImageRide.setImageResource(ride.getRideImage());

        return convertView;
    }
}
