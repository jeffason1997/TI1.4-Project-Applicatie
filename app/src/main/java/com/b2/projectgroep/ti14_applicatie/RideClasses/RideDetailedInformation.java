package com.b2.projectgroep.ti14_applicatie.RideClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

public class RideDetailedInformation extends AppCompatActivity {

    private Ride ride;
    private TextView name;
    private TextView catogoryRide;
    private ImageView imageRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_clicked);

        Intent i = getIntent();
        ride = (Ride) i.getSerializableExtra("RIDE");

        name = (TextView) findViewById(R.id.rideNameClicked_tv);
        name.setText(ride.getName());
    }
}
