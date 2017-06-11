package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.AddCardToListTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetRequestedCardsListener;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetRequestedCardsTak;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTaskListener;
import com.b2.projectgroep.ti14_applicatie.R;

import java.util.ArrayList;
import java.util.List;

public class Employee_requestedlistActivity extends AppCompatActivity implements GetRequestedCardsListener, TableTaskListener {

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //NFC moet uigelezen worden en het cardId moet opgeslagen worden
                String cardId = "150";

                //NFC gegevens worden nu op het pasje geschreven

                String toSend = "{";
                toSend += "\"cardId\":\"" + cardId + "\"";
                toSend += ", ";
                toSend += "\"phoneNumber\":\"" + requests.get(position).getPhoneNumber() + "\"";
                toSend += ", ";
                toSend += "\"firstname\":\"" + requests.get(position).getChild().firstname + "\"";
                toSend += ", ";
                toSend += "\"lastname\":\"" + requests.get(position).getChild().lastname + "\"";
                toSend += "}";

                String[] params = new String[]{toSend};
                AddCardToListTask task = new AddCardToListTask(Employee_requestedlistActivity.this);
                task.execute(params);
                requests.remove(position);
                adapter.notifyDataSetChanged();

            }
        });

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
    public void onSuccesMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
