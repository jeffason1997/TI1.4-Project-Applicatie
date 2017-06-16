package com.b2.projectgroep.ti14_applicatie.DiplomaClasses.AchievementClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivity;

import java.util.ArrayList;

public class AchievementActivity extends AppCompatActivity {

    ArrayList<PersonalActivity> personalActivities;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        personalActivities = (ArrayList<PersonalActivity>) getIntent().getExtras().getSerializable("pa");

        listView = (ListView) findViewById(R.id.achievement_listview);
        listView.setAdapter(new AchievementAdapter(getApplicationContext(), Achievement.calculateAchievements(personalActivities)));
    }
}
