package com.b2.projectgroep.ti14_applicatie.RideClasses;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

public class PersonalActivityDetailedInformation extends AppCompatActivity {

    private PersonalActivity personalActivity;
    private TextView name;
    private TextView descriptionRide;
    private ImageView imageRide;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_clicked);

        Intent i = getIntent();
        personalActivity = (PersonalActivity) i.getSerializableExtra("RIDE");

        Typeface type = Typeface.createFromAsset(getAssets(),"Fonts/Arial_Black.ttf");
        name = (TextView) findViewById(R.id.ride_detailedTitle);
        name.setTypeface(type);
        name.setText(personalActivity.getRide().getName());

        descriptionRide = (TextView) findViewById(R.id.ride_detailedDescription);
        descriptionRide.setText(personalActivity.getRide().getInformation());

        imageRide = (ImageView) findViewById(R.id.ride_detailedImage);
        imageRide.setImageResource(personalActivity.getRide().getRideImage());

        time = (TextView) findViewById(R.id.ride_detaildedTime);
        time.setText(personalActivity.getTime());

    }
}
