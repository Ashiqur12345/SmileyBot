package com.betabots.team.smileybot;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebRequestActivity extends AppCompatActivity {

    EditText emailText;
    TextView responseView;
    ProgressBar progressBar;
    //static final String API_URL = "http://ashman.somee.com/api/symptom/next/?state=%7B%22Symptoms%22%3A%5B%22High+Fever%22%5D%2C%22NotSymptoms%22%3A%5B%22Bleeding%20from%20Nose%20and%20Gums%22%5D%7D";
    static final String API_URL = "http://www.ashman.somee.com/api/symptom/Fever/chain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_request);

        responseView = (TextView) findViewById(R.id.responseView);
        emailText = (EditText) findViewById(R.id.emailText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        new RetrieveFeedTask(API_URL, this.progressBar).execute();
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            pBar.setVisibility(View.VISIBLE);
            responseView.setText("");
        }
        private ProgressBar pBar;
        private String link;
        public RetrieveFeedTask(String url, ProgressBar pBar){
            this.link = url;
            this.pBar = pBar;
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL(this.link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            pBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            //responseView.setText(response);
            // TODO: check this.exception
            // TODO: do something with the feed

            try {
                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                String name = object.getString("Name");
                String query = object.getString("Query");
                JSONArray chain = object.getJSONArray("Chain");
                responseView.setText("Name: "+name);
                responseView.append("Query: "+query);

                responseView.append("Chain: ");

                for (int i = 0; i < chain.length(); i++) {
                    responseView.append("\n"+chain.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
