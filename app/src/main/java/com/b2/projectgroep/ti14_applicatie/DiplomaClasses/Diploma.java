package com.b2.projectgroep.ti14_applicatie.DiplomaClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import java.util.ArrayList;

public class Diploma extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diploma);

        ListView diplomaLV = (ListView) findViewById(R.id.diploma_lv_id);
        ArrayList<Ride> dpVisited = new ArrayList<>(Ride.getTestRides().values());
        DiplomaAdapter dpAdapter = new DiplomaAdapter(getApplicationContext(),dpVisited);
        diplomaLV.setAdapter(dpAdapter);
    }
}
