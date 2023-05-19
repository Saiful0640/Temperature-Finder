package com.temperatureFinder.service;

public interface TemperatureIService {

     String fetchDataFromExternalApi();

    double findByDateAndTime(String date, String time);
}
