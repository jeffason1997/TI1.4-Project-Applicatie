package com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses;

import android.os.AsyncTask;
import android.util.Log;

import com.b2.projectgroep.ti14_applicatie.EmployeeClasses.Name;
import com.b2.projectgroep.ti14_applicatie.EmployeeClasses.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * Created by dionb on 9-6-2017.
 */

public class GetRequestedCardsTak extends AsyncTask<String, Void, String> {
    GetRequestedCardsListener listener;

    String urlString = "http://dion-bartelen.000webhostapp.com/Essteling/getRequestedCards.php";

    //temp url
    //String urlString = "http://82.101.217.193/Essteling/setupTable.php";

    public GetRequestedCardsTak(GetRequestedCardsListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String answer = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            DataInputStream input = new DataInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            answer = reader.readLine().toString();
            String line;
            while ((line = reader.readLine()) != null) {
                answer += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while getting data";
        }
        return answer;
    }

    @Override
    protected void onPostExecute(String s) {
            Log.i("Message", s);
            try {
                JSONArray ja = new JSONArray(s);
                for(int x = 0; x < ja.length(); x++) {
                    JSONObject jo = ja.getJSONObject(x);
                    Name parent = new Name(jo.getString("parent_firstname"), jo.getString("parent_lastname"));
                    String phoneNumber = jo.getString("phone_number");
                    Name child1 = new Name(jo.getString("childfirstname"), jo.getString("childlastname"));
                    Request r = new Request(parent, phoneNumber, child1);
                    listener.onRequestAvailable(r);
                    Log.i("Message", "Request sent");
                }
            } catch (Exception e) {
                e.printStackTrace();
                listener.onErrorMessage("Error while parsing");
            }
        }
}
