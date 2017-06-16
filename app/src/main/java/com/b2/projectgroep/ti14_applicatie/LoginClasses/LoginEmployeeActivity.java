package com.b2.projectgroep.ti14_applicatie.LoginClasses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.LogInTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTaskListener;
import com.b2.projectgroep.ti14_applicatie.EmployeeClasses.Employee_chooseActivity;
import com.b2.projectgroep.ti14_applicatie.R;

public class LoginEmployeeActivity extends AppCompatActivity implements TableTaskListener {

    EditText usernameInput;
    EditText passwordInput;
    ProgressDialog dialog;
    String lastPasswordEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employee);

        usernameInput = (EditText) findViewById(R.id.employee_login_username_input);
        passwordInput = (EditText) findViewById(R.id.employee_login_password_input);
        ImageButton loginButton = (ImageButton) findViewById(R.id.employee_login_confirm_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInTask task = new LogInTask(LoginEmployeeActivity.this);

                String[] params = new String[]{"{\"username\":\"" + usernameInput.getText() + "\"}"};
                task.execute(params);

                lastPasswordEntered = passwordInput.getText().toString();

                dialog = new ProgressDialog(LoginEmployeeActivity.this);
                dialog.setMessage(getResources().getString(R.string.waiting_server_response));
                dialog.show();
            }
        });
    }

    @Override
    public void onSuccesMessage(String s) {
        if(dialog != null) {
            dialog.dismiss();
        }

        if(s.equals(lastPasswordEntered)) {
            Intent i = new Intent(getApplicationContext(), Employee_chooseActivity.class);
            startActivity(i);
        }else {
            Toast.makeText(getApplicationContext(), R.string.wrong_credentials, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorMessage(String s) {
        if(dialog != null) {
            dialog.dismiss();
        }

        Toast.makeText(getApplicationContext(), R.string.wrong_credentials, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        usernameInput.setText("");
        passwordInput.setText("");
    }
}