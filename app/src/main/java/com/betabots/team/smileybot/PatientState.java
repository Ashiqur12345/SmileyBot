package com.betabots.team.smileybot;

import java.util.ArrayList;

/**
 * Created by Ashiqur Rahman on 14-Sep-18.
 */

public class PatientState {
    ArrayList<String> symptoms = new ArrayList<>();
    ArrayList<String> notSymptoms = new ArrayList<>();

    @Override
    public String toString() {
        return "PatientState{" +
                "symptoms=" + symptoms +
                ", notSymptoms=" + notSymptoms +
                '}';
    }
}
