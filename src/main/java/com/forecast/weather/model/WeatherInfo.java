package com.forecast.weather.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class WeatherInfo {

    private double temperature;
    private double minTemperature;
    private double maxTemperature;
    private String location;
    private int zipcode;
    private String description;
    private int humidity;
    private double windSpeed;
    private boolean isCached;
}
