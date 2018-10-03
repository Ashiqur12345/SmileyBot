package com.betabots.team.smileybot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseBotOrOCRActivity extends AppCompatActivity {

    Person patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        patient = (Person) getIntent().getSerializableExtra("PATIENT");
        if(patient == null)finish();

        setContentView(R.layout.activity_choose_bot_or_ocr);

    }

    public void goToBot(View view) {
        Intent intent = new Intent(getApplicationContext(), BotActivity.class);
        intent.putExtra("PATIENT", patient);
        startActivity(intent);

    }

    public void goToOCR(View view) {
        Intent intent = new Intent(getApplicationContext(), com.betabots.team.smileybot.NotMine.MainActivity.class);
        intent.putExtra("PATIENT", patient);
        startActivity(intent);
    }
}
