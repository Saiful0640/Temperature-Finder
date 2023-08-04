package com.temperatureFinder.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Temperature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String time;
    private String date;
    private Double temperature_2m;

    public Temperature(long id, String time, String date, Double temperature_2m) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.temperature_2m = temperature_2m;
    }

    public Temperature() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(Double temperature_2m) {
        this.temperature_2m = temperature_2m;
    }
}
