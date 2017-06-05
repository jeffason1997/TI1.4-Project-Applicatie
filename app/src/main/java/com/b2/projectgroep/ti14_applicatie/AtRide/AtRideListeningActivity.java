package com.b2.projectgroep.ti14_applicatie.AtRide;

import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.InsertIntoTableTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTaskListener;
import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;


public class AtRideListeningActivity extends AppCompatActivity implements TableTaskListener {

    String cardId = "card3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_ride_listening);

        final Ride ride = (Ride) getIntent().getExtras().getSerializable("RIDE");

        Button simulate = (Button) findViewById(R.id.atride_listening_simulate);
        simulate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String key = Ride.getKeyFromRide(ride);
                Calendar c = Calendar.getInstance();
                int minutes = c.get(Calendar.MINUTE);
                int hours = c.get(Calendar.HOUR_OF_DAY) + 2;
                String minutesString = "" + minutes;
                String hoursString = "" + hours;

                if(minutes < 10) {
                    minutesString = "0" + minutes;
                }
                if(hours < 10) {
                    hoursString = "0" + hours;
                }
                String time = hoursString + ":" + minutesString;
                String toSend = "{\"cardId\":\"" + cardId + "\", \"time\":\"" + time + "\", \"rideName\":\"" + key + "\"}";
                InsertIntoTableTask insertTask = new InsertIntoTableTask(AtRideListeningActivity.this);
                String[] params = new String[] {toSend};
                insertTask.execute(params);
            }
        });
    }

    @Override
    public void onSuccesMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
