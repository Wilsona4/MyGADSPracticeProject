package com.funcrib.gadsapp.model;

public class LearningLeaderModel {
    private String name;
    private String country;
    private int hours;

    public LearningLeaderModel(String name, String country, int hours) {
        this.name = name;
        this.country = country;
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}