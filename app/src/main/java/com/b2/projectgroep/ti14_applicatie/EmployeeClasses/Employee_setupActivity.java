package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.b2.projectgroep.ti14_applicatie.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class Employee_setupActivity extends AppCompatActivity {

    public static final String MIME_TEXT_PLAIN = "text/plain";
    private NdefMessage message = null;
    private ProgressDialog dialog;
    private NfcAdapter mNfcAdapter;
    private Button NFCButt;
    private EditText nameParent, surParent, phoneNumber, nameChild, surChild, cardNumber;
    Tag currentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_setup);

        nameParent = (EditText) findViewById(R.id.Esetup_nameParent_id);
        surParent = (EditText) findViewById(R.id.Esetup_surnameParent_id);
        phoneNumber = (EditText) findViewById(R.id.Esetup_phone_id);
        nameChild = (EditText) findViewById(R.id.Esetup_nameChild_id);
        surChild = (EditText) findViewById(R.id.Esetup_surnameChild_id);
        cardNumber = (EditText) findViewById(R.id.Esetup_cardNumber_id);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter==null){
            Toast.makeText(this,"this device doesn't support NFC.",Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        NFCButt = (Button) findViewById(R.id.Esetup_confirm_button);
        NFCButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempText = "Top Employee_setupActivity";
                try {
                    tempText = MakeJsonString();
                } catch (JSONException ex) {
                    System.err.println(ex);
                }
                message = createTextMessage(tempText);

                if (message != null) {
                    dialog = new ProgressDialog(Employee_setupActivity.this);
                    dialog.setMessage("Tag NFC Card please");
                    dialog.show();
                }
            }
        });
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
        if (message != null) {
            writeTag(currentTag, message);
            dialog.dismiss();
            Toast.makeText(this,"Done",Toast.LENGTH_LONG).show();
            finish();
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

    public void writeTag(Tag tag, NdefMessage message) {
        if (tag != null) {
            try {
                Ndef ndefTag = Ndef.get(tag);
                if (ndefTag == null) {
                    NdefFormatable nForm = NdefFormatable.get(tag);
                    if (nForm != null) {
                        nForm.connect();
                        nForm.format(message);
                        nForm.close();
                    }
                } else {
                    ndefTag.connect();
                    ndefTag.writeNdefMessage(message);
                    ndefTag.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public NdefMessage createTextMessage(String content) {
        try {
            byte[] lang = Locale.getDefault().getLanguage().getBytes("UTF-8");
            byte[] text = content.getBytes("UTF-8");

            int langSize = lang.length;
            int textLength = text.length;

            ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + langSize + textLength);
            payload.write((byte) (langSize & 0x1F));
            payload.write(lang, 0, langSize);
            payload.write(text, 0, textLength);
            NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                    NdefRecord.RTD_TEXT, new byte[0],
                    payload.toByteArray());
            return new NdefMessage(new NdefRecord[]{record});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public String MakeJsonString() throws JSONException {
        String message;
        JSONObject json = new JSONObject();

        if (nameParent.getText().toString().isEmpty() || surParent.getText().toString().isEmpty() ||
                phoneNumber.getText().toString().isEmpty() || nameChild.getText().toString().isEmpty() ||
                surChild.getText().toString().isEmpty() || cardNumber.getText().toString().isEmpty())
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Empty Field");
            builder.setMessage("One or multiple fields are empty");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            message = null;

        } else {

            json.put("nameP", nameParent.getText().toString());
            json.put("surnameP", surParent.getText().toString());
            json.put("phoneNumber", phoneNumber.getText().toString());
            json.put("nameC", nameChild.getText().toString());
            json.put("surnameC", surChild.getText().toString());
            json.put("cardNumer", cardNumber.getText().toString());
            message = json.toString();

        }

        System.out.println(message);

        return message;

    }
}
