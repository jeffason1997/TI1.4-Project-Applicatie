package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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
                Intent i = new Intent(getApplicationContext(), Employee_requestedlistActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        Employee_Read_Info.cardNumber = null;
        Employee_Read_Info.childName = null;
        Employee_Read_Info.childSur = null;
        Employee_Read_Info.phoneNumber = null;
        Employee_Read_Info.parentName = null;
        Employee_Read_Info.parentSur = null;
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.employee_choose_setup_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.employee_choose_setup : {
                Intent i = new Intent(getApplicationContext(), Employee_SetupActivity.class);
                startActivity(i);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
