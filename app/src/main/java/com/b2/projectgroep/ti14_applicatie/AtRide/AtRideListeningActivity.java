package com.b2.projectgroep.ti14_applicatie.AtRide;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.icu.util.Calendar;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.InsertIntoTableTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTaskListener;
import com.b2.projectgroep.ti14_applicatie.R;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


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
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC is turned off.", Toast.LENGTH_LONG).show();
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
        Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);
        int hours = c.get(Calendar.HOUR_OF_DAY) + 2;
        String minutesString = "" + minutes;
        String hoursString = "" + hours;

        if(minutes < 10) {
            minutesString = "0" + minutes;
        }
        if(hours < 10) {
            hoursString = "0" + hours;
        }
        String time = hoursString + ":" + minutesString;
        String toSend = "{\"cardId\":\"" + cardId + "\", \"time\":\"" + time + "\", \"rideName\":\"" + key + "\"}";
        InsertIntoTableTask insertTask = new InsertIntoTableTask(AtRideListeningActivity.this);
        String[] params = new String[] {toSend};
        insertTask.execute(params);
    }

    @Override
    public void onSuccesMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        iv.setBackgroundColor(Color.rgb(0, 150, 0));
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
            try {
                JSONObject jo = new JSONObject(message);
                String cardId = "card" + jo.getString("cardNummer");
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
