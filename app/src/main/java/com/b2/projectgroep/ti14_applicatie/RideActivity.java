package com.b2.projectgroep.ti14_applicatie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetTableTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetTableTaskListener;

import java.util.ArrayList;

public class RideActivity extends AppCompatActivity implements GetTableTaskListener {

    ArrayList<Ride> rides = new ArrayList<>();
    ListView ride_listView;
    ArrayAdapter mAdapter;
    private ListView listViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        rides = new ArrayList<>();

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

        GetTableTask getTableTask = new GetTableTask(this);
        String[] params = new String[] {"{\"cardId\":\"card3\"}"};
        getTableTask.execute(params);
    }

    @Override
    public void onRideAvailable(Ride r) {
        if(!rides.contains(r) && r != null) {
            rides.add(r);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorMessage(String s) {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_loading_data), Toast.LENGTH_LONG).show();
    }
}
