package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

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

}
