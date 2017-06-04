package com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses;

import android.os.AsyncTask;

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
 * Created by dionb on 1-6-2017.
 */

public class GetTableTask extends AsyncTask<String, Void, String> {
    GetTableTaskListener listener;
    String urlString = "http://dion-bartelen.000webhostapp.com/Essteling/get.php";

    public GetTableTask(GetTableTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String answer = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

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
            listener.onErrorMessage("Error while getting data");
        }
        return answer;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONArray ja = new JSONArray(s);
            for(int x = 0; x < ja.length(); x++) {
                JSONObject ride = ja.getJSONObject(x);
                Ride r = Ride.getRideFromName(ride.getInt("rideName"));
                //r.setTime(ride.getString("time"));
                listener.onRideAvailable(r);
            }
        } catch (JSONException e) {
            listener.onErrorMessage(s);
        }
    }
}
