package com.b2.projectgroep.ti14_applicatie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Employee_chooseActivity extends AppCompatActivity {

    Button read;
    Button setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_choose);

        read = (Button) findViewById(R.id.choose_read_id);
        setup = (Button) findViewById(R.id.choose_setup_id);
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
