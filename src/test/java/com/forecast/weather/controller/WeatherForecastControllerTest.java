package com.forecast.weather.controller;

import com.forecast.weather.exception.InvalidZipcodeException;
import com.forecast.weather.model.WeatherInfo;
import com.forecast.weather.service.WeatherForecastService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class WeatherForecastControllerTest {

    @InjectMocks
    private WeatherForecastController weatherForecastController;
    @Mock
    private WeatherForecastService weatherForecastService;

    @Test
    public void getWeatherInfoByZipCode(){
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setTemperature(300.0);
        weatherInfo.setLocation("XYZ");
        weatherInfo.setDescription("Cloudy");
        Mockito.when(weatherForecastService.getWeatherInformation(123456)).thenReturn(weatherInfo);
        ResponseEntity<WeatherInfo> weather = weatherForecastController.getWeatherForecastByZipCode(123456);
        Assertions.assertEquals(HttpStatus.OK, weather.getStatusCode());
        Assertions.assertEquals(300.0, weather.getBody().getTemperature());
        Assertions.assertEquals("Cloudy", weather.getBody().getDescription());
    }

    @Test
    public void getWeatherInfoByZipCodeFailure(){
        Mockito.when(weatherForecastService.getWeatherInformation(123456)).thenThrow(new InvalidZipcodeException());
        ResponseEntity<WeatherInfo> weather = weatherForecastController.getWeatherForecastByZipCode(123456);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, weather.getStatusCode());
        Assertions.assertEquals(null, weather.getBody());
    }

}
