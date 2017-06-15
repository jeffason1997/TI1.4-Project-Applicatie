package com.b2.projectgroep.ti14_applicatie.CardClasses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetCardsTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetCardsWithPhoneTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetTableTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetTableTaskListener;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTaskListener;
import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivityAdapter;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivityDetailedInformation;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalListActivity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jeffrey on 9-6-2017.
 */

public class SelectCardActivity extends AppCompatActivity implements GetCardsTask{

    private ProgressDialog dialog;
    private ListView lv_cards,listViewer;
    private ArrayAdapter mAdapter;
    private ArrayList<Card> cards;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_selecter);

        String phoneNumber = getIntent().getExtras().getString("phoneNumber");

        cards = new ArrayList<>();

        lv_cards =  (ListView) findViewById(R.id.cards);

        mAdapter = new CardListAdapter((this.getApplicationContext()), cards);
        lv_cards.setAdapter(mAdapter);

        listViewer = (ListView) findViewById(R.id.cards);
        listViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PersonalListActivity.class);
                i.putExtra("cardID", "card" + cards.get(position).getId());
                i.putExtra("name",cards.get(position).getName());
                i.putExtra("surname",cards.get(position).getSurName());
                startActivity(i);
            }
        });

        GetCardsWithPhoneTask getCards = new GetCardsWithPhoneTask(this);
        String[] params = new String[] {"{\"phoneNumber\":\"" + phoneNumber + "\"}"};
        getCards.execute(params);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.waiting_server_response));
        dialog.show();

    }

    @Override
    public void onCardAvailable(Card c) {
        if(!cards.contains(c) && c != null) {
            dialog.dismiss();
            cards.add(c);
            Collections.sort(cards);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorMessage(String message) {
        dialog.dismiss();
        if(message.equals("No results")) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_no_data_returned), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_loading_data), Toast.LENGTH_LONG).show();
        }
    }
}
