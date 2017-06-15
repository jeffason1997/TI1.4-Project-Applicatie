package com.b2.projectgroep.ti14_applicatie;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.LogInTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTaskListener;
import com.b2.projectgroep.ti14_applicatie.AtRide.AtRideActivity;
import com.b2.projectgroep.ti14_applicatie.CardClasses.SelectCardActivity;
import com.b2.projectgroep.ti14_applicatie.EmployeeClasses.Employee_chooseActivity;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalListActivity;

public class LoginActivity extends AppCompatActivity {

    private ImageButton btn;
    TextView tvHead,appName;
    EditText logincode;
    ProgressDialog dialog;
    String loginEmployee = "1234";
//    String loginGuest = "4321";
    String loginAtLocation = "0000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Typeface type = Typeface.createFromAsset(getAssets(),"Fonts/Arial_Black.ttf");
        tvHead = (TextView) findViewById(R.id.Login_titelText);
        tvHead.setTypeface(type);
        appName = (TextView) findViewById(R.id.AppName);
        appName.setTypeface(type);
        logincode = (EditText) findViewById(R.id.Login_number_id);

        this.btn = (ImageButton) findViewById(R.id.Esetup_confirm_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputFromUser = logincode.getText().toString();
                if(inputFromUser.equals("")) {
                    Toast.makeText(getApplicationContext(), "Put in a phonenumber", Toast.LENGTH_LONG).show();
                } else if(!inputFromUser.equals(loginEmployee) && !inputFromUser.equals(loginAtLocation)) {
                    Intent i = new Intent(getApplicationContext(), SelectCardActivity.class);
                    i.putExtra("phoneNumber", logincode.getText().toString());
                    startActivity(i);
                } else if(!inputFromUser.equals(loginEmployee)) {
                    Intent i = new Intent(getApplicationContext(), AtRideActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), LoginEmployeeActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        logincode.setText("");
    }
}











