package com.b2.projectgroep.ti14_applicatie;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

   private Button btn;
   TextView tvHead;
    EditText logincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface type = Typeface.createFromAsset(getAssets(),"Fonts/Arial_Black.ttf");
        tvHead = (TextView) findViewById(R.id.Login_titelText);
        tvHead.setTypeface(type);
        logincode = (EditText) findViewById(R.id.Login_number_id);
      
        this.btn = (Button) findViewById(R.id.Login_Button_id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(logincode.getText().toString().equals("0000")) {
                    Intent i = new Intent(getApplicationContext(), Employee_chooseActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), RideActivity.class);
                    startActivity(i);
                }
            }
        });

    }
}











