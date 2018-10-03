package com.betabots.team.smileybot;

import java.util.ArrayList;

/**
 * Created by Ashiqur Rahman on 14-Sep-18.
 */

public class DiseaseProbability {
    Disease disease;
    double Probability;
}


class Disease{

    public String Name;
    public String Field;
    public ArrayList<String> Symptoms;

    public Disease()
    {
        Symptoms = new ArrayList<>();
    }

}
