package com.betabots.team.smileybot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.betabots.team.smileybot.Model.DoctorListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FindDoctorActivity extends AppCompatActivity {

    String diseaseField;
    ProgressBar progressBar;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private ArrayList<DoctorListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        diseaseField = getIntent().getStringExtra("EXPERTISE");

        setContentView(R.layout.activity_find_doctor);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        //every item has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listItems = new ArrayList<>();

        new FetchDoctorTask().execute();
    }

    class FetchDoctorTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        private String url = getString(R.string.host) + "/api/doctor/" + diseaseField + "/info";

        public FetchDoctorTask() {
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
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            System.out.println("response: " + response);
            if (!response.equals("null")) {

                JSONArray doctors = null;
                try {
                    doctors = (JSONArray) new JSONArray(response);

                    for (int i = 0; i < doctors.length(); i++) {
                        JSONObject doctor = (JSONObject) doctors.get(i);

                        String dName = doctor.getString("Name");
                        String dExpertise = doctor.getString("Expertise");
                        String dLocation = doctor.getString("Location");
                        String dContact = doctor.getString("Contact");

                        DoctorListItem listItem = new DoctorListItem(
                                dName,
                                "Expertise: " + dExpertise,
                                "Contact: " + dContact,
                                "Location: " + dLocation
                        );
                        listItems.add(listItem);

                    }
                    adapter = new DoctorAdapter(getApplicationContext(), listItems);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                System.err.println("Error fetching doctor");
                return;
            }
        }
    }


    class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

        private Context context;
        private List<DoctorListItem> listItems;

        public DoctorAdapter(Context context, List listItem) {
            this.context = context;
            this.listItems = listItem;
        }

        @Override
        public DoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.doctor_list, parent, false);

            return new DoctorAdapter.ViewHolder(v, context, listItems);
        }

        @Override
        public void onBindViewHolder(DoctorAdapter.ViewHolder holder, int position) {
            DoctorListItem listItem = listItems.get(position);

            holder.name.setText(listItem.getName());
            holder.expertise.setText(listItem.getExpertise());
            holder.contact.setText(listItem.getContact());
            holder.location.setText(listItem.getLocation());
        }


        @Override
        public int getItemCount() {
            return listItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView name;
            public TextView expertise;
            public TextView contact;
            public TextView location;


            public ViewHolder(View view, Context ctx, List<DoctorListItem> items) {
                super(view);
                listItems = items;
                //get the Activity Context
                context = ctx;

                view.setOnClickListener(this);

                name = (TextView) view.findViewById(R.id.docName);
                expertise = (TextView) view.findViewById(R.id.docExpertise);
                contact = (TextView) view.findViewById(R.id.docContact);
                location = (TextView) view.findViewById(R.id.docLocation);

            }

            @Override
            public void onClick(View v) {
                //Get int position
                int position = getAdapterPosition();
                DoctorListItem item = listItems.get(position);
                //  Intent intent = new Intent(context, MyActivity.class);

                String contact = item.getContact();

                Intent intent = new Intent(Intent.ACTION_CALL);
                Toast.makeText(FindDoctorActivity.this, ""+contact, Toast.LENGTH_SHORT).show();

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FindDoctorActivity.this, new String[]{Manifest.permission.CALL_PHONE},1);
                    return;
                }
                String number = contact.replaceFirst("Contact: ", "");
                Uri uri = Uri.parse("tel:" + number);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }
}
