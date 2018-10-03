package com.betabots.team.smileybot;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.betabots.team.smileybot.Model.DiseaseListItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    Person patient;
    private List<DiseaseListItem> diseaseListItems;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        patient = (Person) getIntent().getSerializableExtra("PATIENT");

        setContentView(R.layout.activity_report);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        //every item has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        diseaseListItems = new ArrayList<>();

        new FetchDiseaseTask().execute();


    }


    class FetchDiseaseTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        // http://localhost:57716/api/estimate/prob/?symptoms=%5B%22Nausea%22%2C%22High+Fever%22%2C%22Vomiting%22%5D
        private String url = getString(R.string.host) + "/api/estimate/prob/?symptoms=";

        public FetchDiseaseTask() {
            Gson gson = new Gson();
            String state = gson.toJson(patient.symptoms);
            this.url += state;
            System.out.println(url);
        }

        protected String doInBackground(Void... urls) {

            try {
                URL url = new URL(this.url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(15000);

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);//.append("\n");
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
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            if(! response.equals("null")) {

                JSONArray diseases = null;
                try {
                    diseases = (JSONArray) new JSONArray(response);

                    for (int i = 0; i < diseases.length(); i++) {
                        JSONObject item1 = (JSONObject) diseases.get(i);

                        JSONObject disease = (JSONObject) item1.getJSONObject("Disease");
                        String dName = disease.getString("Name");
                        String dField = disease.getString("Field");
                        Double dProb = item1.getDouble("Probability") * 100;
                        dProb = Double.valueOf(new DecimalFormat("#.##").format(dProb));
                        if(dProb > 0){
                            //createQuestion("Disease: "+dName+" \nProbability: "+dProb);


                            DiseaseListItem diseaseListItem = new DiseaseListItem(
                                    dName,
                                    "Probability: "+dProb+" %",
                                    dField
                            );
                            diseaseListItems.add(diseaseListItem);
                        }
                    }
                    adapter = new MyAdapter(getApplicationContext(), diseaseListItems);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else {
                System.err.println("End of all symptoms");
                return;
            }
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private Context context;
        private List<DiseaseListItem> diseaseListItems;

        public MyAdapter(Context context, List listItem) {
            this.context = context;
            this.diseaseListItems = listItem;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row, parent, false);

            return new ViewHolder(v, context, (ArrayList<DiseaseListItem>) diseaseListItems);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DiseaseListItem diseaseListItem = diseaseListItems.get(position);
            holder.name.setText(diseaseListItem.getName());
            holder.description.setText(diseaseListItem.getDescription());
        }


        @Override
        public int getItemCount() {
            return diseaseListItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView name;
            public TextView description;

            public ViewHolder(View view, Context ctx, ArrayList<DiseaseListItem> items) {
                super(view);
                diseaseListItems = items;
                //get the Activity Context
                context = ctx;

                view.setOnClickListener(this);

                name = (TextView) view.findViewById(R.id.title);
                description = (TextView) view.findViewById(R.id.description);

            }

            @Override
            public void onClick(View v) {
                //Get int position
                int position = getAdapterPosition();
                DiseaseListItem item = diseaseListItems.get(position);
                //  Intent intent = new Intent(context, MyActivity.class);

                Intent intent = new Intent(getApplicationContext(), FindDoctorActivity.class);
                intent.putExtra("EXPERTISE", item.getExpertise());
                startActivity(intent);
            }
        }
    }
}
