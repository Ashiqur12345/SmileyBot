package com.betabots.team.smileybot.Model;

/**
 * Created by Ashiqur Rahman on 14-Sep-18.
 */

public class DoctorListItem {

    private String name;
    private String expertise;
    private String contact;
    private String location;

    public DoctorListItem(String name, String expertise, String contact, String location) {
        this.name = name;
        this.expertise = expertise;
        this.contact = contact;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
