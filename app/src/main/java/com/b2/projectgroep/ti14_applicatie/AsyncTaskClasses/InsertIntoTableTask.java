package com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses;

import android.os.AsyncTask;

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

public class InsertIntoTableTask extends AsyncTask<String, Void, String> {
    TableTaskListener listener;
    String urlString = "https://dion-bartelen.000webhostapp.com/Essteling/post.php";

    public InsertIntoTableTask(TableTaskListener listener) {
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
        if(s.equals("Succes")) {
            listener.onSuccesMessage(s);
        } else {
            listener.onErrorMessage(s);
        }
    }
}
