package com.betabots.team.smileybot;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ashiqur Rahman on 13-Sep-18.
 */

public class Person implements Serializable{
    int id;
    String name;
    double age;
    String gender;
    String email;
    String bloodGroup;

    ArrayList<String> symptoms, notSymptoms;
    String predictedDisease;

    public Person(int id, String name, double age, String gender, String email, String bloodGroup) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.symptoms = new ArrayList<>();
        this.notSymptoms = new ArrayList<>();
    }


    public Person() {
        this.symptoms = new ArrayList<>();
        this.notSymptoms = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<String> symptoms) {
        this.symptoms = symptoms;
    }

    public ArrayList<String> getNotSymptoms() {
        return notSymptoms;
    }

    public void setNotSymptoms(ArrayList<String> notSymptoms) {
        this.notSymptoms = notSymptoms;
    }

    public String getPredictedDisease() {
        return predictedDisease;
    }

    public void setPredictedDisease(String predictedDisease) {
        this.predictedDisease = predictedDisease;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", bloodgroup='" + bloodGroup + '\'' +
                ", symptoms=" + symptoms +
                ", notSymptoms=" + notSymptoms +
                ", predictedDisease='" + predictedDisease + '\'' +
                '}';
    }
}
