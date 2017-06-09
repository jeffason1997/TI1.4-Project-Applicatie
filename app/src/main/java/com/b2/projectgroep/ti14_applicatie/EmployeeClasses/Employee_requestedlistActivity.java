package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetRequestedCardsListener;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetRequestedCardsTak;
import com.b2.projectgroep.ti14_applicatie.R;

import java.util.ArrayList;
import java.util.List;

public class Employee_requestedlistActivity extends AppCompatActivity implements GetRequestedCardsListener {

    ListView listView;
    ArrayList<Request> requests;
    RequestListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_requestedlist);

        listView = (ListView) findViewById(R.id.employee_request_list);
        requests = new ArrayList<>();
        adapter = new RequestListAdapter(getApplicationContext(), requests);
        listView.setAdapter(adapter);

        GetRequestedCardsTak task = new GetRequestedCardsTak(this);
        task.execute();
    }

    @Override
    public void onRequestAvailable(Request request) {
        if(!requests.contains(request)) {
            requests.add(request);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
    }
}
