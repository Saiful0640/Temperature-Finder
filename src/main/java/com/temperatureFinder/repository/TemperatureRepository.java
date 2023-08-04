package com.temperatureFinder.repository;

import com.temperatureFinder.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface TemperatureRepository extends JpaRepository<Temperature,Long> {



    @Query(value = "SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Temperature h WHERE h.date = :date AND h.time = :time")
    boolean hasData(@Param("date") String date, @Param("time") String time);

  // @Query(value = "SELECT t.temperature_2m from temperature t WHERE t.date = :date AND time = :time ")
    Temperature findByDateAndTime(String date, String time);

    // Add a custom method to find temperature_2m by date and time
    Temperature findByDateAndTimeLike(String date, String time);

}
