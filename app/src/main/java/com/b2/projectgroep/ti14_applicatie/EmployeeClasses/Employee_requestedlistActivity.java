package com.b2.projectgroep.ti14_applicatie.EmployeeClasses;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.AddCardToListTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.CreateTableTask;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetRequestedCardsListener;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.GetRequestedCardsTak;
import com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses.TableTaskListener;
import com.b2.projectgroep.ti14_applicatie.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Employee_requestedlistActivity extends AppCompatActivity implements GetRequestedCardsListener, TableTaskListener {

    ListView listView;
    ArrayList<Request> requests;
    RequestListAdapter adapter;
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private NdefMessage message = null;
    private NfcAdapter mNfcAdapter;
    private String cardNumber, phoneNumber, firstname, surname, pFirstname, pSurname;
    private ProgressDialog dialog;
    Tag currentTag;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_requestedlist);

        listView = (ListView) findViewById(R.id.employee_request_list);
        requests = new ArrayList<>();
        adapter = new RequestListAdapter(getApplicationContext(), requests);
        listView.setAdapter(adapter);
        con = this;

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, R.string.no_nfc_supported, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog = new ProgressDialog(con);
                dialog.setMessage(getResources().getString(R.string.tag_nfc_card));
                dialog.show();

                phoneNumber = requests.get(position).getPhoneNumber();
                firstname = requests.get(position).getChild().firstname;
                surname = requests.get(position).getChild().lastname;
                pFirstname = requests.get(position).getParent().firstname;
                pSurname = requests.get(position).getParent().lastname;

                while (cardNumber != null && phoneNumber != null && firstname != null && surname != null && pFirstname == null && pSurname == null) {
                    requests.remove(position);
                    adapter.notifyDataSetChanged();
                    finish();
                }

            }
        });

        dialog = new ProgressDialog(con);
        dialog.setMessage(getResources().getString(R.string.waiting_server_response));
        dialog.show();

        GetRequestedCardsTak task = new GetRequestedCardsTak(this);
        task.execute();
    }

    @Override
    public void onRequestAvailable(Request request) {
        if(dialog != null) {
            dialog.dismiss();
        }
        if (!requests.contains(request)) {
            requests.add(request);
            adapter.notifyDataSetChanged();
        }
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
        handleIntent(intent);
        while (cardNumber == null) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String tempText = "";
        try {
            tempText = MakeJsonString();
        } catch (JSONException ex) {
            System.err.println(ex);
        }
        message = createTextMessage(tempText);

        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (message != null) {
            writeTag(currentTag, message);
            dialog.dismiss();
            Toast.makeText(this, R.string.done, Toast.LENGTH_LONG).show();
        }

        String toSend = "{";
        toSend += "\"cardId\":\"" + cardNumber + "\"";
        toSend += ", ";
        toSend += "\"phoneNumber\":\"" + phoneNumber + "\"";
        toSend += ", ";
        toSend += "\"firstname\":\"" + firstname + "\"";
        toSend += ", ";
        toSend += "\"lastname\":\"" + surname + "\"";
        toSend += "}";

        String[] params = new String[]{toSend};
        AddCardToListTask task = new AddCardToListTask(Employee_requestedlistActivity.this);
        task.execute(params);

        cardNumber = null;
        phoneNumber = null;
        firstname = null;
        surname = null;
        pFirstname = null;
        pSurname = null;
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        System.out.println(action);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new Employee_requestedlistActivity.NdefReaderTask().execute(tag);

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
                    new Employee_requestedlistActivity.NdefReaderTask().execute(tag);
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

        json.put("nameP", pFirstname);
        json.put("surnameP", pSurname);
        json.put("phoneNumber", phoneNumber);
        json.put("nameC", firstname);
        json.put("surnameC", surname);
        json.put("cardNumber", cardNumber);
        message = json.toString();

        createCorrespondingTable(cardNumber);

        String toReturn = new String(Base64.encodeToString(message.getBytes(), 0));
        return toReturn;

    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        readCard(readText(ndefRecord));
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
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        protected void readCard(String result) {
            result = new String(Base64.decode(result, 0));
            if (result != null) {
                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                    cardNumber = json.getString("cardNumber");
                } catch (Exception ex) {
                    System.err.println(ex);
                }
            }
        }
    }

    private void createCorrespondingTable(String s) {
        String toSend = "{\"cardId\":\"" + s + "\"}";
        CreateTableTask createTask = new CreateTableTask(this);
        String[] params = new String[]{toSend};
        createTask.execute(params);
    }

    @Override
    public void onSuccesMessage(String s) {
        if(dialog != null) {
            dialog.dismiss();
        }
        Log.i("Message", s);
    }

    @Override
    public void onErrorMessage(String s) {
        if(dialog != null) {
            dialog.dismiss();
        }
        Log.i("Message", s);
    }
}
