package com.temperatureFinder.repository;

import com.temperatureFinder.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface TemperatureRepository extends JpaRepository<Temperature,Long> {

    @Query(value = "SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Temperature h")
    boolean hasData();

    Temperature findByDateAndTime(String date, String time);

}
