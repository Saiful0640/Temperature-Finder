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

@RestController
@RequestMapping("api/v1")
public class TemperatureController {

    @Autowired
    private TemperatureService temperatureService;
    @Autowired
    private TemperatureRepository temperatureRepository;

    @GetMapping("/fetch/hourly/temp")
    public ResponseEntity<SaveResponseMessage> fetchDataFromExternalApiAndSave() {
        boolean hasData = temperatureRepository.hasData();

        if (!hasData) {
            String data = temperatureService.fetchDataFromExternalApi();

            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode jsonNode = objectMapper.readTree(data);
                JsonNode hourlyNode = jsonNode.get("hourly");
                JsonNode timeArray = hourlyNode.get("time");
                JsonNode temperatureArray = hourlyNode.get("temperature_2m");

                List<Temperature> hourlyList = new ArrayList<>();

                for (int i = 0; i < timeArray.size(); i++) {
                    Temperature hourly = new Temperature();

                    String dateTimeString = timeArray.get(i).asText();
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);

                    String date = dateTime.toLocalDate().toString();
                    String time = dateTime.toLocalTime().toString();

                    hourly.setDate(date);
                    hourly.setTime(time);
                    hourly.setTemperature_2m(temperatureArray.get(i).asDouble());
                    hourlyList.add(hourly);
                }

                List<Temperature> savedHourlyList = temperatureRepository.saveAll(hourlyList);
                if (savedHourlyList != null && !savedHourlyList.isEmpty()) {
                   // return ResponseEntity.status(HttpStatus.OK).body(" ResponseMgs : Operation Successful Status code: " + HttpStatus.OK.value());
                    SaveResponseMessage saveResponseMessage = new SaveResponseMessage();

                    saveResponseMessage.setResponseMessage("Operation successful");
                    saveResponseMessage.setResponseCode(200);
                    return ResponseEntity.ok(saveResponseMessage);

                } else {
                    SaveResponseMessage saveResponseMessage = new SaveResponseMessage();
                    saveResponseMessage.setResponseMessage("Operation not successful");
                    saveResponseMessage.setResponseCode(201);
                    return ResponseEntity.ok(saveResponseMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SaveResponseMessage saveResponseMessage = new SaveResponseMessage();
        saveResponseMessage.setResponseMessage("Data Already Exist");
        saveResponseMessage.setResponseCode(409);
        return ResponseEntity.ok(saveResponseMessage);
    }


    @PostMapping("/fetch/temp/datetime")
    public ResponseEntity <GetResponseMessage> getTemperatureByDateAndTime(
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
