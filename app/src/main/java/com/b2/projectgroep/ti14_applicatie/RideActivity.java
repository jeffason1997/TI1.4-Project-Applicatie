package com.b2.projectgroep.ti14_applicatie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RideActivity extends AppCompatActivity {

    ArrayList<Ride> ride = new ArrayList<>();
    ListView ride_lv;
    ArrayAdapter mAdapter;
    private ListView listViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        ride.add(new Ride("Python", "Achterbaan", R.drawable.python
        ));
        ride.add(new Ride("Draaimolen", "rustige attractie", R.drawable.draaimolen_nostalgisch));
        ride.add(new Ride("Houten achtbaan", "Achtbaan", R.drawable.draaimolen_nostalgisch));
        ride.add(new Ride("Lange Jan", "rustige attractie", R.drawable.draaimolen_nostalgisch));
        ride.add(new Ride("", "rustige attractie", R.drawable.draaimolen_nostalgisch));

        ride_lv =  (ListView) findViewById(R.id.ride_lv);

        mAdapter = new RideAdapter((this.getApplicationContext()), ride);
        ride_lv.setAdapter(mAdapter);

        listViewer = (ListView) findViewById(R.id.ride_lv);
        listViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), RideClicked.class);
                i.putExtra("RIDE", ride.get(position));
                startActivity(i);
            }
        });
    }
}
