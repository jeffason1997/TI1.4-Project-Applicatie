package com.b2.projectgroep.ti14_applicatie;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

   private Button btn;
   TextView tvHead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface type = Typeface.createFromAsset(getAssets(),"Fonts/Arial_Black.ttf");
        tvHead = (TextView) findViewById(R.id.Login_titelText);
        tvHead.setTypeface(type);
      
        this.btn = (Button) findViewById(R.id.Login_Button_id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RideActivity.class);
                startActivity(i);
            }
        });

    }
}











