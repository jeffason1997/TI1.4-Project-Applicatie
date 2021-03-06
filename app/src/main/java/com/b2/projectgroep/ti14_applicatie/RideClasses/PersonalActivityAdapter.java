package com.b2.projectgroep.ti14_applicatie.RideClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2.projectgroep.ti14_applicatie.R;

import java.util.ArrayList;

/**
 * Created by harm on 18-5-2017.
 */

public class PersonalActivityAdapter extends ArrayAdapter<PersonalActivity> {
    public PersonalActivityAdapter(Context context, ArrayList<PersonalActivity> personalActivities) {
        super(context, 0, personalActivities);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        PersonalActivity personalActivity = getItem(position);

        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lv_row_parent, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.rideName_tv);
        tvName.setText(personalActivity.getRide().getName());

        TextView tvCatogoryRide = (TextView) convertView.findViewById(R.id.catogoryRide_tv);
        tvCatogoryRide.setText(personalActivity.getRide().getCatogoryRide());

        ImageView imaImageRide = (ImageView) convertView.findViewById(R.id.rideImage_image);
        imaImageRide.setImageResource(personalActivity.getRide().getRideImage());

        TextView timeRide = (TextView) convertView.findViewById(R.id.ride_time);
        timeRide.setText(personalActivity.getTime());

        return convertView;
    }
}
