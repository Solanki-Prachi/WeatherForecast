package com.forecast.weather.service;

import com.forecast.weather.model.WeatherInfo;
import org.springframework.stereotype.Service;

@Service
public interface WeatherForecastService {
    public WeatherInfo getWeatherInformation(Integer zipcode);
}
