package com.b2.projectgroep.ti14_applicatie.AtRide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivityAdapter;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import java.util.ArrayList;
import java.util.List;

public class AtRideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_ride);

        ListView rideOptions = (ListView) findViewById(R.id.atlocation_rideoptions);
        final ArrayList<Ride> allOptions = new ArrayList<>(Ride.getTestRides().values());
        RideAdapter paa = new RideAdapter(getApplicationContext(), allOptions);
        rideOptions.setAdapter(paa);

        rideOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), AtRideListeningActivity.class);
                i.putExtra("RIDE", allOptions.get(position));
                startActivity(i);
            }
        });
    }
}
