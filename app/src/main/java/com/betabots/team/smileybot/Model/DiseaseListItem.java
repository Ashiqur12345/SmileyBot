package com.betabots.team.smileybot.Model;

/**
 * Created by paulodichone on 3/22/17.
 */

public class DiseaseListItem {
    private String name;
    private String description;
    private String expertise;

    public DiseaseListItem(String name, String description, String expertise) {
        this.name = name;
        this.description = description;
        this.expertise = expertise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
}
