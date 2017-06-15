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

public class LoginActivity extends AppCompatActivity implements TableTaskListener {

    private ImageButton btn;
    TextView tvHead,appName;
    EditText logincode;
    ProgressDialog dialog;
//    String loginEmployee = "1234";
//    String loginGuest = "4321";
//    String loginAtLocation = "0000";


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
                if(inputFromUser.length() > 6) {
                    Intent i = new Intent(getApplicationContext(), SelectCardActivity.class);
                    i.putExtra("phoneNumber", logincode.getText().toString());
                    startActivity(i);
                } else {
                    LogInTask task = new LogInTask(LoginActivity.this);
                    String[] params = new String[]{"{\"input\":\"" + inputFromUser + "\"}"};
                    task.execute(params);

                    dialog = new ProgressDialog(LoginActivity.this);
                    dialog.setMessage("Waiting for server response...");
                    dialog.show();
                }
            }
        });
    }

    @Override
    public void onSuccesMessage(String s) {
        if(dialog != null) {
            dialog.dismiss();
        }
        if(s.equals("ride")) {
            Intent i = new Intent(getApplicationContext(), AtRideActivity.class);
            startActivity(i);
        } else if(s.equals("employee")) {
            Intent i = new Intent(getApplicationContext(), Employee_chooseActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onErrorMessage(String s) {
        if(dialog != null) {
            dialog.dismiss();
        }
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_emplyee_code ), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        logincode.setText("");
    }
}











