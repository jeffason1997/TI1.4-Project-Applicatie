package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.b2.projectgroep.ti14_applicatie.DiplomaClasses.Diploma;
import com.b2.projectgroep.ti14_applicatie.R;

public class Employee_chooseActivity extends AppCompatActivity {

    ImageButton read;
    ImageButton setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_choose);

        read = (ImageButton) findViewById(R.id.choose_read_id);
        setup = (ImageButton) findViewById(R.id.choose_setup_id);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Employee_readActivity.class);
                startActivity(i);
            }
        });
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Employee_setupActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.employee_choose, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_employee_choose_requested : {
                Intent i = new Intent(getApplicationContext(), Employee_requestedlistActivity.class);
                startActivity(i);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

}
