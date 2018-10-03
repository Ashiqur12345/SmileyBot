package com.betabots.team.smileybot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

public class BotActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout parentLayout;
    ScrollView rootScrollView;
    ProgressBar progressBar;
    Button predictBtn;

    TranslateAnimation animateAppear, tvAppear;

    public Person patient;
    public String symptomInProgress = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init Patient attributes
//        patient = (Person) getIntent().getSerializableExtra("PATIENT");
//
//        if(patient == null)super.onBackPressed();

        patient = (Person) getIntent().getSerializableExtra("PATIENT");
        //Initialize scene
        setContentView(R.layout.activity_bot);
        rootScrollView = (ScrollView) findViewById(R.id.rootScrollView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        initParent();

        animateAppear = new TranslateAnimation(
                100,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        animateAppear.setDuration(500);
        animateAppear.setFillAfter(true);

        tvAppear = new TranslateAnimation(
                -100,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                0);                // toYDelta
        tvAppear.setDuration(500);
        tvAppear.setFillAfter(true);


        predictBtn = (Button) findViewById(R.id.myButt);
        predictBtn.setEnabled(false);
        predictBtn.setVisibility(View.INVISIBLE);
        predictBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                predictDisease();
            }
        });

        introduction();

    }

    private void predictDisease() {
        Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
        intent.putExtra("PATIENT", patient);
        startActivity(intent);
    }

    private void introduction() {
        createQuestion("Hello "+patient.name+"! How are you?");

        Button yesBtn = new Button(getApplicationContext());
        yesBtn.setText("I am fine.");

        Button noBtn = new Button(getApplicationContext());
        noBtn.setText("I am not feeling well");

        ArrayList<View> tvs = new ArrayList<>();
        tvs.add(yesBtn);
        tvs.add(noBtn);
        createAnswer(tvs);
    }

    private void initParent() {
        parentLayout = new LinearLayout(this);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,0,0,70);
        parentLayout.setLayoutParams(layoutParams);
        parentLayout.setPadding(10, 10, 10, 10);

        rootScrollView.addView(parentLayout);
    }


    @SuppressLint("ResourceAsColor")
    void createQuestion(String question) {
        LinearLayout qLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 50, 50, 50);
        qLayout.setLayoutParams(layoutParams);
        qLayout.setPadding(25,50,25,50);
        //qLayout.setBackgroundColor(Color.parseColor("#FF3A3A"));
        qLayout.setHorizontalGravity(View.TEXT_ALIGNMENT_VIEW_END);

        GradientDrawable border = new GradientDrawable();
        //border.setColor(R.color.myWhite); //white background
        border.setStroke(2, R.color.myWhite); //black border with full opacity
        border.setCornerRadius(25);
        qLayout.setBackground(border);



        TextView tv = new TextView(getApplicationContext());

        tv.setText(question);
        tv.setTextSize(25);
        tv.setTextColor(R.color.colorPrimaryDark);
        tv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/century_gothic.ttf"));
        tv.startAnimation(tvAppear);
        qLayout.addView(tv);
        parentLayout.addView(qLayout);
        scrollToBottom();

    }
    void createAnswer() {
        LinearLayout qLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 30);
        qLayout.setLayoutParams(layoutParams);
        qLayout.setPadding(10,10,10,35);
        //qLayout.setBackgroundColor(Color.parseColor("#FFC03A"));
        qLayout.setHorizontalGravity(View.TEXT_ALIGNMENT_VIEW_START);
        TextView tv = new TextView(getApplicationContext());

        tv.setText("Answer");
        tv.setTextSize(22);

        qLayout.addView(tv);
        parentLayout.addView(qLayout);
    }
    void createAnswer(View v) {
        LinearLayout qLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        qLayout.setLayoutParams(layoutParams);
        //qLayout.setBackgroundColor(Color.parseColor("#FFC03A"));
        qLayout.setHorizontalGravity(View.TEXT_ALIGNMENT_VIEW_START);


        if(v instanceof Button){
            v.setBackgroundResource(R.drawable.button_style);
            v.startAnimation(animateAppear);
            v.setOnClickListener(this);

            ((Button)v).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/century_gothic.ttf"));
        }
        qLayout.addView(v);
        parentLayout.addView(qLayout);
        allActiveViews.add(v);
        scrollToBottom();
    }

    void createAnswer(ArrayList<View> viewList) {
        LinearLayout qLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        qLayout.setLayoutParams(layoutParams);
        //qLayout.setBackgroundColor(Color.parseColor("#FFC03A"));
        qLayout.setHorizontalGravity(View.TEXT_ALIGNMENT_VIEW_START);

        for (View v: viewList) {
            if(v instanceof Button){
                v.setBackgroundResource(R.drawable.button_style);
                v.setOnClickListener(this);
                v.startAnimation(animateAppear);
                ((Button)v).setTypeface(Typeface.createFromAsset(getAssets(),"fonts/century_gothic.ttf"));
            }


            qLayout.addView(v);
        }
        parentLayout.addView(qLayout);
        allActiveViews.addAll(viewList);
        scrollToBottom();
    }

    private void scrollToBottom() {
        rootScrollView.post(new Runnable() {
            @Override
            public void run() {
                rootScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    ArrayList<View> allActiveViews = new ArrayList<>();
    //ScrollableNumberPicker numberPicker;
    MaterialNumberPicker numberPicker;
    @Override
    public void onClick(View v) {

        if(allActiveViews != null && !allActiveViews.isEmpty()){
            allActiveViews.remove(v);
            v.setEnabled(false);
            for (View view : allActiveViews) {
                if(view instanceof Button){
                    view.setEnabled(false);
                    TranslateAnimation anime = new TranslateAnimation(
                            -5,                 // fromXDelta
                            0,                 // toXDelta
                            -5,  // fromYDelta
                            0);                // toYDelta
                    anime.setDuration(1000);
                    anime.setFillAfter(true);

                    ViewGroup layout = (ViewGroup) view.getParent();
                    if(null!=layout) { //for safety only  as you are doing onClick
                        layout.removeView(view);
                    }
                    v.startAnimation(anime);
                }
            }
            allActiveViews.clear();
        }

        if(v instanceof Button){
            Button b = (Button)v;
            final String buttonText = b.getText().toString();
            if(buttonText.equals(getString(R.string.no))){
                patient.notSymptoms.add(symptomInProgress);
                symptomInProgress = "";
                new FetchSymptomTask().execute();
                return;
            }
            else if(buttonText.equals(getString(R.string.yes))){
                // TODO: 14-Sep-18 Chaining stuff
                if(symptomInProgress.equals("Fever")){
//                    int output = (int) (output_start + ((output_end - output_start) / (input_end - input_start)) * (progress - input_start));

                    //numberPicker = new ScrollableNumberPicker(getApplicationContext());

                    numberPicker = new MaterialNumberPicker(getApplicationContext());
                    numberPicker.setMinValue(98);
                    numberPicker.setMaxValue(108);
                    numberPicker.setValue(100);
                    numberPicker.setTextSize(50);
                    //numberPicker.setStepSize(1);

                    TextView tv = new TextView(getApplicationContext());
                    tv.setText("  ° F  ");
                    tv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/century_gothic.ttf"));
                    Button fevButton = new Button(getApplicationContext());
                    fevButton.setText("Set Fever");

                    createQuestion("How acute is your fever?");
                    ArrayList<View> views = new ArrayList<>();
                    views.add(numberPicker);
                    views.add(tv);
                    views.add(fevButton);
                    createAnswer(views);
                }
                else{
                    new FetchChainTask(symptomInProgress).execute();
                    return;
                }
            }
            else{
                if(buttonText.contains("fine")){
                    createQuestion("Nice to hear it. Good bye. ");
                    return;
                }
                else if(buttonText.contains("am not feeling")){
                    new FetchSymptomTask().execute();
                    return;
                }

                else if(buttonText.contains("Set Fever")){
                    int fevVal = numberPicker.getValue();

                    if(fevVal < 101){
                        patient.symptoms.add("Low Fever");
                    }
                    else if(fevVal < 103){
                        patient.symptoms.add("Medium Fever");
                    }
                    else{
                        patient.symptoms.add("High Fever");
                    }
                    TranslateAnimation anime = new TranslateAnimation(
                            -5,                 // fromXDelta
                            0,                 // toXDelta
                            -5,  // fromYDelta
                            0);                // toYDelta
                    anime.setDuration(1000);
                    anime.setFillAfter(true);
                    v.startAnimation(anime);
                    numberPicker.setEnabled(false);
                    symptomInProgress = "";
                    new FetchSymptomTask().execute();
                    return;
                }
                new FetchChainTask(buttonText+ " "+symptomInProgress).execute();
            }
        }
    }


    class FetchSymptomTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        private String url = getString(R.string.host) + "/api/symptom/next/?state=";

        public FetchSymptomTask() {
            PatientState ps = new PatientState();
            ps.symptoms = patient.symptoms;
            ps.notSymptoms = patient.notSymptoms;

            Gson gson = new Gson();
            String state = gson.toJson(ps);
            this.url += state;
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
                    JSONObject nextSymptom = (JSONObject) new JSONTokener(response).nextValue();
                    String name = nextSymptom.getString("Name");
                    String query = nextSymptom.getString("Query");
                    //JSONArray chain = object.getJSONArray("Chain");

                    symptomInProgress = name;
                    createQuestion(query);

                    Button yesBtn = new Button(getApplicationContext());
                    yesBtn.setText(getString(R.string.yes));

                    Button noBtn = new Button(getApplicationContext());
                    noBtn.setText(getString(R.string.no));

                    ArrayList<View> tvs = new ArrayList<>();
                    tvs.add(yesBtn);
                    tvs.add(noBtn);
                    createAnswer(tvs);

                } catch (JSONException e) {
                    createQuestion("Sorry, I have failed to fetch symptoms. Please try again.");
                }
            }
            else {
                createQuestion("I have found some matches");
                predictDisease();
                return;
            }
        }
    }
    class FetchChainTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        private String url = getString(R.string.host);
        private String symptom;
        public FetchChainTask(String symptom) {
            this.symptom = symptom;
            this.url += "/api/symptom/"+symptom+"/chain";
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
                    JSONObject symptomChain = (JSONObject) new JSONTokener(response).nextValue();
                    String name = symptomChain.getString("Name");
                    String query = symptomChain.getString("Query");
                    JSONArray chain = symptomChain.getJSONArray("Chain");

                    symptomInProgress = name;
                    createQuestion(query);

                    ArrayList<View> tvs = new ArrayList<>();

                    for (int i = 0; i < chain.length(); i++) {
                        Button option = new Button(getApplicationContext());
                        option.setText(chain.getString(i));
                        tvs.add(option);
                        createAnswer(option);
                    }

                    //createAnswer(tvs);

                } catch (JSONException e) {
                    createQuestion("Sorry, I have failed to fetch details. Please try again.");
                }
            }
            else {
                patient.symptoms.add(this.symptom);
                symptomInProgress = "";
                if(patient.symptoms.size() >= 3 && !predictBtn.isEnabled()){
                    predictBtn.setEnabled(true);
                    predictBtn.setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,                 // fromXDelta
                            0,                 // toXDelta
                            predictBtn.getHeight(),  // fromYDelta
                            0);                // toYDelta
                    animate.setDuration(1000);
                    animate.setFillAfter(true);
                    predictBtn.startAnimation(animate);
                }
                new FetchSymptomTask().execute();
            }
        }
    }
}

