package com.temperatureFinder.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temperatureFinder.model.SaveResponseMessage;
import com.temperatureFinder.model.Temperature;
import com.temperatureFinder.repository.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TemperatureService implements TemperatureIService {



    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TemperatureRepository temperatureRepository;


    @Override
    public String fetchDataFromExternalApi() {
        String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=23.777176&longitude=90.399452&current_weather=true&hourly=temperature_2m";
        return restTemplate.getForObject(apiUrl, String.class);
    }

    @Override
    public SaveResponseMessage fetchDataAndSaveHourlyTemperatures() {
        String data = fetchDataFromExternalApi();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(data);
            JsonNode hourlyNode = jsonNode.get("hourly");
            JsonNode timeArray = hourlyNode.get("time");
            JsonNode temperatureArray = hourlyNode.get("temperature_2m");

            List<Temperature> hourlyList = new ArrayList<>();

            for (int i = 0; i < timeArray.size(); i++) {
                String dateTimeString = timeArray.get(i).asText();
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);

                String date = dateTime.toLocalDate().toString();
                String time = dateTime.toLocalTime().toString();

                boolean hasData = temperatureRepository.hasData(date, time);
                if (!hasData) {
                    Temperature hourly = new Temperature();
                    hourly.setDate(date);
                    hourly.setTime(time);
                    hourly.setTemperature_2m(temperatureArray.get(i).asDouble());
                    hourlyList.add(hourly);
                }
            }

            if (!hourlyList.isEmpty()) {
                List<Temperature> savedHourlyList = temperatureRepository.saveAll(hourlyList);
                if (!savedHourlyList.isEmpty()) {
                    SaveResponseMessage saveResponseMessage = new SaveResponseMessage();
                    saveResponseMessage.setResponseMessage("Operation successful");
                    saveResponseMessage.setResponseCode(200);
                    return saveResponseMessage;
                } else {
                    SaveResponseMessage saveResponseMessage = new SaveResponseMessage();
                    saveResponseMessage.setResponseMessage("Operation not successful");
                    saveResponseMessage.setResponseCode(201);
                    return saveResponseMessage;
                }
            } else {
                SaveResponseMessage saveResponseMessage = new SaveResponseMessage();
                saveResponseMessage.setResponseMessage("Data already exists");
                saveResponseMessage.setResponseCode(409);
                return saveResponseMessage;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SaveResponseMessage saveResponseMessage = new SaveResponseMessage();
        saveResponseMessage.setResponseMessage("Error occurred");
        saveResponseMessage.setResponseCode(500);
        return saveResponseMessage;
    }




    @Override
    public double findByDateAndTime(String date, String time) {

        Temperature temperature = temperatureRepository.findByDateAndTime(date, time);
        if (temperature != null) {
            return temperature.getTemperature_2m();
        } else {
            return 0;
        }

    }


    public Temperature getTemperatureByDateAndTime(String date, String time) {
        return temperatureRepository.findByDateAndTimeLike(date, time);
    }

}
