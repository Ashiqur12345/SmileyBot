
package com.betabots.team.smileybot;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    Button login;
    Button reg;
    Button skip;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        reg = findViewById(R.id.reg);
        //skip = findViewById(R.id.skip);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }


    public void skipAuth(View view) {
        Intent i = new Intent(getApplicationContext(), ChooseBotOrOCRActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("PATIENT", getAnUser());
        startActivity(i);
    }

    private Person getAnUser() {
        Person patient = new Person();
        patient.name = "Human";
        patient.email = "human@mail.com";
        patient.age = 20;
        patient.gender = "Other";
        return patient;
    }
}
