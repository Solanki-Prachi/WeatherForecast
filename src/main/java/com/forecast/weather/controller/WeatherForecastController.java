package com.forecast.weather.controller;

import com.forecast.weather.exception.InvalidZipcodeException;
import com.forecast.weather.model.WeatherInfo;
import com.forecast.weather.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherForecastController {

    @Autowired
    private WeatherForecastService weatherForecastService;

    @GetMapping("/v1/weather")
    public ResponseEntity<WeatherInfo> getWeatherForecastByZipCode(@RequestParam Integer zipcode) {
        WeatherInfo forecast = null;
        try {
            forecast = weatherForecastService.getWeatherInformation(zipcode);
        } catch (InvalidZipcodeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(forecast);
    }
}
