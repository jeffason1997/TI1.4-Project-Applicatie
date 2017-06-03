package com.b2.projectgroep.ti14_applicatie.RideClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.b2.projectgroep.ti14_applicatie.R;

import java.util.ArrayList;

public class RideActivity extends AppCompatActivity {

    ArrayList<Ride> rides = new ArrayList<>();
    ListView ride_listView;
    ArrayAdapter mAdapter;
    private ListView listViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        rides = Ride.getTestRides();

        ride_listView =  (ListView) findViewById(R.id.ride_lv);

        mAdapter = new RideAdapter((this.getApplicationContext()), rides);
        ride_listView.setAdapter(mAdapter);

        listViewer = (ListView) findViewById(R.id.ride_lv);
        listViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), RideDetailedInformation.class);
                i.putExtra("RIDE", rides.get(position));
                startActivity(i);
            }
        });
    }
}
