package com.temperatureFinder.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temperatureFinder.model.GetResponseMessage;
import com.temperatureFinder.model.SaveResponseMessage;
import com.temperatureFinder.model.Temperature;
import com.temperatureFinder.repository.TemperatureRepository;
import com.temperatureFinder.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1")
public class TemperatureController {

    @Autowired
    private TemperatureService temperatureService;


    @GetMapping("/fetch/hourly/temp")
    public ResponseEntity<SaveResponseMessage> fetchDataFromExternalApiAndSave() {
        SaveResponseMessage responseMessage = temperatureService.fetchDataAndSaveHourlyTemperatures();
        return ResponseEntity.status(responseMessage.getResponseCode()).body(responseMessage);
    }




    @GetMapping("/temperature")
    public ResponseEntity<GetResponseMessage> getTemperatureByDateAndTime1(
            @RequestParam String date,
            @RequestParam String time
    ) {
        Temperature temperature = temperatureService.getTemperatureByDateAndTime(date, time);

        GetResponseMessage response = new GetResponseMessage();
        if (temperature != null) {
            response.setTemperature(String.valueOf(temperature.getTemperature_2m()));
            response.setResponseCode(200);
            response.setResponseMessage("Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setTemperature(null);
            response.setResponseCode(404);
            response.setResponseMessage("Temperature not found for the specified date and time.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/fetch/temp/datetime")
    public ResponseEntity<GetResponseMessage> getTemperatureByDateAndTime(
            @RequestParam String date,
            @RequestParam String time
    ) {
        double temperatures = temperatureService.findByDateAndTime(date, time);
        if (temperatures == 0) {
            GetResponseMessage getResponseMessage = new GetResponseMessage();
            getResponseMessage.setResponseMessage("Operation not successful");
            getResponseMessage.setResponseCode(201);
            getResponseMessage.setTemperature("");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getResponseMessage);
        } else {
            GetResponseMessage getResponseMessage = new GetResponseMessage();
            getResponseMessage.setTemperature(String.valueOf(temperatures));
            getResponseMessage.setResponseMessage("Operation successful");
            getResponseMessage.setResponseCode(200);
            return ResponseEntity.ok(getResponseMessage);
        }
    }




}
