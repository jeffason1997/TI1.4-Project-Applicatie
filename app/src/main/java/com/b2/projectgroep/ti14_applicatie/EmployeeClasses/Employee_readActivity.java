package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.b2.projectgroep.ti14_applicatie.R;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Employee_readActivity extends AppCompatActivity {

    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private TextView parentName,parentSur,phoneNumber,childName,childSur,cardNumber;
    private NfcAdapter mNfcAdapter;
    private ProgressDialog dialog;
    Tag currentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_read);

            parentName = (TextView) findViewById(R.id.Eread_nameParent_id);
            parentSur = (TextView) findViewById(R.id.Eread_surnameParent_id);
            phoneNumber = (TextView) findViewById(R.id.Eread_phonenumber_id);
            phoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (phoneNumber.getText().toString().contains("06"))
                        dailContactPhone(phoneNumber.getText().toString());
                }
            });
            childName = (TextView) findViewById(R.id.Eread_nameChild_id);
            childSur = (TextView) findViewById(R.id.Eread_surnameChild_id);
            cardNumber = (TextView) findViewById(R.id.Eread_cardNumber_id);

        if(Employee_Read_Info.getParentName() != null && Employee_Read_Info.getParentSur() != null && Employee_Read_Info.getPhoneNumber() != null && Employee_Read_Info.getChildName() != null && Employee_Read_Info.getChildSur() != null && Employee_Read_Info.getCardNumber() != null) {
            parentName.setText(Employee_Read_Info.getParentName());
            parentSur.setText(Employee_Read_Info.getParentSur());
            phoneNumber.setTextColor(getResources().getColor(R.color.colorBlue));
            SpannableString content = new SpannableString(Employee_Read_Info.getPhoneNumber());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            phoneNumber.setText(content);
            childName.setText(Employee_Read_Info.getChildName());
            childSur.setText(Employee_Read_Info.getChildSur());
            cardNumber.setText(Employee_Read_Info.getCardNumber());
        } else {
            dialog = new ProgressDialog(Employee_readActivity.this);
            dialog.setMessage(getResources().getString(R.string.tag_nfc_card));
            dialog.show();
        }


            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if (mNfcAdapter == null) {
                Toast.makeText(this, R.string.no_nfc_supported, Toast.LENGTH_LONG).show();
                finish();
                return;
            }

//        handleIntent(getIntent());
    }

    private void dailContactPhone(String number){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null)));
    }


    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        stopForegroundDispatch(this, mNfcAdapter);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            byte[] payload = record.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageCodeLength = payload[0] & 0063;
            String message1 = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
            return message1;
        }

        @Override
        protected void onPostExecute(String result) {
            result = new String(Base64.decode(result, 0));
            if (result != null) {
                JSONObject json = null;
                try{
                json = new JSONObject(result);
                } catch (Exception ex){
                    System.err.println(ex);
                }

                 if(json!=null){
                     try {
                         Employee_Read_Info.setParentName(json.getString("nameP"));
                         Employee_Read_Info.setParentSur(json.getString("surnameP"));
                         Employee_Read_Info.setPhoneNumber(json.getString("phoneNumber"));
                         Employee_Read_Info.setChildName(json.getString("nameC"));
                         Employee_Read_Info.setChildSur(json.getString("surnameC"));
                         Employee_Read_Info.setCardNumber(json.getString("cardNumber"));
                     } catch (Exception e){
                         System.err.println(e);
                     }
                 }
                parentName.setText(Employee_Read_Info.getParentName());
                parentSur.setText(Employee_Read_Info.getParentSur());
                phoneNumber.setTextColor(getResources().getColor(R.color.colorBlue));
                SpannableString content = new SpannableString(Employee_Read_Info.getPhoneNumber());
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                phoneNumber.setText(content);
                childName.setText(Employee_Read_Info.getChildName());
                childSur.setText(Employee_Read_Info.getChildSur());
                cardNumber.setText(Employee_Read_Info.getCardNumber());
                dialog.dismiss();
            }
        }
    }
}
