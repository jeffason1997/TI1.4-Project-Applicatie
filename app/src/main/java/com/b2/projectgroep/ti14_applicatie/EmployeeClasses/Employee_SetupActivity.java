package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.b2.projectgroep.ti14_applicatie.R;

public class Employee_SetupActivity extends AppCompatActivity {

    EditText cardInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee__setup);

        cardInput = (EditText) findViewById(R.id.employee_setup_cardnumber);
        Button button = (Button) findViewById(R.id.employee_setup_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
