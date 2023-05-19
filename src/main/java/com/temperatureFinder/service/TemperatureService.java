package com.temperatureFinder.service;

import com.temperatureFinder.model.Temperature;
import com.temperatureFinder.repository.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TemperatureService implements TemperatureIService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TemperatureRepository temperatureRepository;

    @Override
    public String fetchDataFromExternalApi() {
        String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=23.777176&longitude=90.399452&current_weather=true&hourly=temperature_2m";
        String response = restTemplate.getForObject(apiUrl, String.class);
       return  response;
    }

    @Override
    public double findByDateAndTime(String date, String time) {
        Temperature temperature = temperatureRepository.findByDateAndTime(date, time);
        if(temperature != null){
            return  temperature.getTemperature_2m();
        }else{
            return 0;
        }

    }
}
