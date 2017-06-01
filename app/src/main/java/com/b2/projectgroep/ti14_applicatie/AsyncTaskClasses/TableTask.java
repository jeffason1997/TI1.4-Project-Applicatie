package com.b2.projectgroep.ti14_applicatie.AsyncTaskClasses;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dionb on 1-6-2017.
 */

public class TableTask extends AsyncTask<String, Void, String> {
    TableTaskListener listener;

    public TableTask(TableTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String answer = "";
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(params[1]);
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
        listener.onSuccesMessage(s);
    }
}
