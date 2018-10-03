package com.betabots.team.smileybot;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    ProgressBar progressBar;
    String[] bitems2;

    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mNameView;
    private EditText mPassView, mPassView2;
    private EditText mbDate;
    private Spinner mbGroup;
    private Spinner mgender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mNameView = (AutoCompleteTextView) findViewById(R.id.name);
        mPassView = (EditText) findViewById(R.id.password);
        mPassView2 = (EditText) findViewById(R.id.password2);
        mbDate = (EditText) findViewById(R.id.bDate);
        mbGroup = (Spinner) findViewById(R.id.bGroup);
        mgender = (Spinner) findViewById(R.id.gender);

        Spinner gender = findViewById(R.id.gender);
        String[] items = new String[]{"Male", "Female", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        gender.setAdapter(adapter);


        Spinner bGroup = findViewById(R.id.bGroup);
        String[] bitems = new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        bitems2 = new String[]{"A-P", "A-N", "B-P", "B-N", "AB-P", "AB-N", "O-P", "O-N"};
        ArrayAdapter<String> badapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bitems);
        bGroup.setAdapter(badapter);

        Button mEmailSignInButton = (Button) findViewById(R.id.registerBtn);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString();
                String pass = mPassView.getText().toString();
                String pass2 = mPassView2.getText().toString();
                String name = mNameView.getText().toString();
                String gender = mgender.getSelectedItem().toString();
                String bgroup = mbGroup.getSelectedItem().toString();
                String bdate = mbDate.getText().toString();

                if( !pass.equals(pass2)){
                    Toast.makeText(RegisterActivity.this, "Password mismatch", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(name.length() < 3 || pass.length() < 3){
                    Toast.makeText(RegisterActivity.this, "Name too short", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(! email.contains("@")){
                    Toast.makeText(RegisterActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(bdate.length() < 10){
                    Toast.makeText(RegisterActivity.this, "Date is Invalid", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    new RegistrationTask().execute();
                }

//            Person patient = getRegisteredPatient();
//            if(patient != null){
//                Intent i = new Intent(getApplicationContext(), ChooseBotOrOCRActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                i.putExtra("PATIENT", patient);
//                startActivity(i);
//            }
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    private Person getRegisteredPatient() {
        Person patient = new Person();
        patient.name = "Ashiqur Rahman";
        patient.email = "ash@mail.com";
        patient.age = 20;
        patient.gender = "Male";
        return patient;
    }


    class RegistrationTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        private String url = getString(R.string.host);

        public RegistrationTask() {
            String email = mEmailView.getText().toString();
            String pass = mPassView.getText().toString();
            String pass2 = mPassView2.getText().toString();
            String name = mNameView.getText().toString();
            String gender = mgender.getSelectedItem().toString();
            String bgroup = mbGroup.getSelectedItem().toString();
            String bdate = mbDate.getText().toString();

            //string email, string password, string name, string gender, string bdate, string bgroup

            this.url += "/api/user/signup?"
                    +"email="+email
                    +"&password="+pass
                    +"&name="+name
                    +"&gender="+gender
                    +"&bdate="+bdate
                    +"&bgroup="+getBG(bgroup);

            System.out.println(this.url);
        }

        private String getBG(String bgroup) {
            if(bgroup.equals("A+")){
                return "A-P";
            }
            else if(bgroup.equals("A-")){
                return "A-N";
            }
            else if(bgroup.equals("B+")){
                return "A-P";
            }
            else if(bgroup.equals("A+")){
                return "B-P";
            }
            else if(bgroup.equals("B-")){
                return "B-N";
            }
            else if(bgroup.equals("AB+")){
                return "AB-P";
            }
            else if(bgroup.equals("AB-")){
                return "AB-N";
            }
            else if(bgroup.equals("O+")){
                return "O-P";
            }
            else if(bgroup.equals("O-")){
                return "O-N";
            }
            else return "";

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
            System.out.println(response);
            if(! response.equals("null")) {

                try {
                    JSONObject personJson = (JSONObject) new JSONTokener(response).nextValue();
                    int id = personJson.getInt("ID");
                    String name = personJson.getString("Name");
                    String email = personJson.getString("Email");
                    String gender = personJson.getString("Gender");
                    String bDate = personJson.getString("BirthDate");
                    String bGroup = personJson.getString("BloodGroup");

                    Person patient = new Person(id, name, calcAge(bDate), gender, email, bGroup);
                    Intent i = new Intent(getApplicationContext(), ChooseBotOrOCRActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra("PATIENT", patient);
                    startActivity(i);


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG).show();
            }
        }

        private double calcAge(String strDate)
        {
            System.out.println("got date: "+strDate);
            SimpleDateFormat dt = new SimpleDateFormat("mm-dd-yyyy");
            Date date = null;
            try {
                date = dt.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();

                return 0;
            }

            double years = 0;

            //create calendar object for birth day
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTimeInMillis(date.getTime());

            //create calendar object for current day
            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);

            //Get difference between years
            years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            return years / 100;
        }
    }
}
