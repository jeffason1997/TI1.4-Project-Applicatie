package com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dionb on 13-6-2017.
 */

public class LogInTask extends AsyncTask<String, Void, String> {

    TableTaskListener listener;

    String urlString = "https://dion-bartelen.000webhostapp.com/Essteling/login.php";

    public LogInTask(TableTaskListener listener) {
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
        try {
            JSONArray jo = new JSONArray(s);
            String password = jo.getJSONObject(0).getString("password");
            listener.onSuccesMessage(password);
        } catch (Exception e) {
            listener.onErrorMessage(s);
        }
        super.onPostExecute(s);
    }
}
