package com.betabots.team.smileybot.NotMine.report_helper;

/**
 * Created by ranadepto on 6/2/18.
 */

public class AttributeValue
{
    String attribute, value;
    double accuracy;

    public AttributeValue(String attribute, String value, double accuracy)
    {
        this.attribute = attribute;
        this.value = value;
        this.accuracy = accuracy;
    }

    public String getAttribute()
    {
        return attribute;
    }

    public String getValue()
    {
        return value;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAttribute(String attribute)
    {
        this.attribute = attribute;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}
