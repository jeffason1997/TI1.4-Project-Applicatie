package com.b2.projectgroep.ti14_applicatie;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AtRide.AtRideActivity;
import com.b2.projectgroep.ti14_applicatie.EmployeeClasses.Employee_chooseActivity;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalListActivity;

public class LoginActivity extends AppCompatActivity {

   private Button btn;
   TextView tvHead;
    EditText logincode;
    String loginEmployee = "1234";
    String loginGuest = "4321";
    String loginAtLocation = "0000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Typeface type = Typeface.createFromAsset(getAssets(),"Fonts/Arial_Black.ttf");
        tvHead = (TextView) findViewById(R.id.Login_titelText);
        tvHead.setTypeface(type);
        logincode = (EditText) findViewById(R.id.Login_number_id);
      
        this.btn = (Button) findViewById(R.id.Esetup_confirm_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logincode.getText().toString().equals(loginEmployee)) {
                    Intent i = new Intent(getApplicationContext(), Employee_chooseActivity.class);
                    startActivity(i);
                } else if (logincode.getText().toString().equals(loginAtLocation)) {
                    Intent i = new Intent(getApplicationContext(), AtRideActivity.class);
                    startActivity(i);
                } else if (!logincode.getText().toString().equals("")) {
                    Intent i = new Intent(getApplicationContext(), PersonalListActivity.class);
                    i.putExtra("cardId", "card" + logincode.getText().toString());
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.wrong_login, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}











