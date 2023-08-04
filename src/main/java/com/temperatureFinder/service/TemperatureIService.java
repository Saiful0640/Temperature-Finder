package com.temperatureFinder.service;

import com.temperatureFinder.model.SaveResponseMessage;
import com.temperatureFinder.model.Temperature;

public interface TemperatureIService {

    String fetchDataFromExternalApi();

    public SaveResponseMessage fetchDataAndSaveHourlyTemperatures();

    double findByDateAndTime(String date, String time);

    public Temperature getTemperatureByDateAndTime(String date, String time);


}
