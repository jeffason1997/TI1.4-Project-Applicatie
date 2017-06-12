package com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses;

import android.os.AsyncTask;

import com.b2.projectgroep.ti14_applicatie.CardClasses.Card;
import com.b2.projectgroep.ti14_applicatie.RideClasses.PersonalActivity;
import com.b2.projectgroep.ti14_applicatie.RideClasses.Ride;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jeffrey on 12-6-2017.
 */

public class GetCardsWithPhoneTask extends AsyncTask<String,Void,String> {
    GetCardsTask listener;

    String urlString = "http://dion-bartelen.000webhostapp.com/Essteling/getCardsWithPhoneNumber.php";

    //temp url
    //String urlString = "http://82.101.217.193/Essteling/get.php";

    public GetCardsWithPhoneTask(GetCardsTask listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String answer = "Start";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            System.out.println(params[0]);

            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(params[0]);
            DataInputStream input = new DataInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            answer = reader.readLine().toString();
            String line;
            while ((line = reader.readLine()) != null) {
                answer += line;
            }
            input.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while getting data";
        }
        return answer;
    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println(s);
        try {
            JSONArray ja = new JSONArray(s);
            for(int x = 0; x < ja.length(); x++) {
                JSONObject card = ja.getJSONObject(x);
                String name = card.getString("firstname");
                String surname = card.getString("lastname");
                String id = card.getString("cardId");
                Card c = new Card(name,surname,id);
                listener.onCardAvailable(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onErrorMessage(s);
        }
    }
}
