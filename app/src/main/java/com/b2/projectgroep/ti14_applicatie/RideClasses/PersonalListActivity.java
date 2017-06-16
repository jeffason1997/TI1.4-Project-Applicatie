package com.b2.projectgroep.ti14_applicatie.RideClasses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetTableTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetTableTaskListener;

import com.b2.projectgroep.ti14_applicatie.DiplomaClasses.AchievementActivity;
import com.b2.projectgroep.ti14_applicatie.DiplomaClasses.DiplomaActivity;
import com.b2.projectgroep.ti14_applicatie.R;

import java.util.ArrayList;
import java.util.Collections;

public class PersonalListActivity extends AppCompatActivity implements GetTableTaskListener {

    ArrayList<PersonalActivity> personalActivities = new ArrayList<>();
    ListView ride_listView;
    ArrayAdapter mAdapter;
    private ListView listViewer;
    String name,surname;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        String cardNumber = getIntent().getExtras().getString("cardID");
        name = getIntent().getExtras().getString("name");
        surname = getIntent().getExtras().getString("surname");

        personalActivities = new ArrayList<>();

        ride_listView = (ListView) findViewById(R.id.ride_lv);

        mAdapter = new PersonalActivityAdapter((this.getApplicationContext()), personalActivities);
        ride_listView.setAdapter(mAdapter);

        listViewer = (ListView) findViewById(R.id.ride_lv);
        listViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PersonalActivityDetailedInformation.class);
                i.putExtra("RIDE", personalActivities.get(position));
                startActivity(i);
            }
        });

        GetTableTask getTableTask = new GetTableTask(this);
        String[] params = new String[]{"{\"cardId\":\"" + cardNumber + "\"}"};
        getTableTask.execute(params);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.waiting_server_response));
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.parent_menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.parent_menu_diploma: {
                Intent i = new Intent(getApplicationContext(), DiplomaActivity.class);
                i.putExtra("name", name);
                i.putExtra("surname", surname);
                i.putExtra("personalActivities",personalActivities);
                startActivity(i);
                return true;
            }
            case R.id.parent_menu_achievements : {
                Intent i = new Intent(getApplicationContext(), AchievementActivity.class);
                i.putExtra("name", name);
                i.putExtra("surname", surname);
                i.putExtra("pa",personalActivities);
                startActivity(i);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onRideAvailable(PersonalActivity activity) {
        if (!personalActivities.contains(activity) && activity != null) {
            dialog.dismiss();
            personalActivities.add(activity);
            Collections.sort(personalActivities);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorMessage(final String s) {
        dialog.dismiss();
        if (s.equals("No results")) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_no_data_returned), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_loading_data), Toast.LENGTH_LONG).show();
        }
    }
}
