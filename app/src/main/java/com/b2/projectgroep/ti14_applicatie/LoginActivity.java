package com.b2.projectgroep.ti14_applicatie;


import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTaskListener;

public class LoginActivity extends AppCompatActivity implements TableTaskListener {

   private Button btn;
   TextView tvHead;
    EditText logincode;
    String loginEmployee = "1234";
    String loginGuest = "4321";


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
                if(logincode.getText().toString().equals(loginEmployee)) {
                    Intent i = new Intent(getApplicationContext(), Employee_chooseActivity.class);
                    startActivity(i);
                } else if(logincode.getText().toString().equals(loginGuest)){
                    Intent i = new Intent(getApplicationContext(), RideActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(),R.string.wrong_login, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //TableTask tt = new TableTask(this);
        //Database aanmaken
        //String params[] = new String[] {"http://dion-bartelen.000webhostapp.com/Essteling/setupTable.php", "{\"cardId\":\"card3\"}"};
        //Database verwijderen
        //String params[] = new String[] {"http://dion-bartelen.000webhostapp.com/Essteling/deleteTable.php", "{\"cardId\":\"card3\"}"};
        //Database ophalen
        //String params[] = new String[] {"http://dion-bartelen.000webhostapp.com/Essteling/get.php", "{\"cardId\":\"card3\"}"};
        //Aan database toevoegen
        //String params[] = new String[] {"https://dion-bartelen.000webhostapp.com/Essteling/post.php", "{\"cardId\":\"card3\", \"time\":\"1120\", \"rideName\":\"Python\"}"};
        //tt.execute(params);
    }

    @Override
    public void onSuccesMessage(String s) {
        Log.i("Message", s);
    }

    @Override
    public void onErrorMessage(String s) {
        Log.i("Message", s);
    }
}











