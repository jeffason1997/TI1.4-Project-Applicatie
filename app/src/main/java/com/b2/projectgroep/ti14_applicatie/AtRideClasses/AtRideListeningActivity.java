package com.b2.projectgroep.ti14_applicatie.AtRideClasses;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageButton;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.InsertIntoTableTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTaskListener;
import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;


public class AtRideListeningActivity extends AppCompatActivity implements TableTaskListener {

    NfcAdapter nfcAdapter;
    Ride ride;
    ImageButton iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_ride_listening);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            Toast.makeText(this, R.string.no_nfc_supported, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, R.string.nfc_off, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        ride = (Ride) getIntent().getExtras().getSerializable("RIDE");
        iv = (ImageButton) findViewById(R.id.atlocation_listener_background);
        iv.setBackgroundColor(Color.rgb(0, 0, 150));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void postPersonalActivity(String cardId) {
        String key = Ride.getKeyFromRide(ride);
        Date now = new Date();
        String time = android.text.format.DateFormat.format("HH:mm", now).toString();
        String toSend = "{\"cardId\":\"" + cardId + "\", \"time\":\"" + time + "\", \"rideName\":\"" + key + "\"}";
        InsertIntoTableTask insertTask = new InsertIntoTableTask(AtRideListeningActivity.this);
        String[] params = new String[] {toSend};
        insertTask.execute(params);
    }

    @Override
    public void onSuccesMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        iv.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        iv.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv.setBackgroundColor(Color.rgb(0, 0, 150));
            }
        }, 1000);
    }

    @Override
    public void onErrorMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        iv.setBackgroundColor(Color.rgb(150, 0, 0));
        iv.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv.setBackgroundColor(Color.rgb(0, 0, 150));
            }
        }, 1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void readMessage(Ndef ndef) {
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            String message = new String(ndefMessage.getRecords()[0].getPayload());
            message = message.substring(3);
            System.out.println(message);
            message = new String(Base64.decode(message.getBytes(), 0));
            System.out.println(message);
            try {
                JSONObject jo = new JSONObject(message);
                String cardId = "card" + jo.getString("cardNumber");
                postPersonalActivity(cardId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ndef.close();

        } catch (IOException | FormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        setupForegroundDispatch(this, nfcAdapter);
    }

    @Override
    protected void onPause() {
        stopForegroundDispatch(this, nfcAdapter);
        super.onPause();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            Ndef ndef = Ndef.get(tag);
            readMessage(ndef);
        }
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);
        adapter.enableForegroundDispatch(activity, pendingIntent, null, null);
    }


    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }
}
