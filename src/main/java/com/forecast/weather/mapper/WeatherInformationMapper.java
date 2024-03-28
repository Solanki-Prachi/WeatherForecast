package com.forecast.weather.mapper;

import com.forecast.weather.model.WeatherForecast;
import com.forecast.weather.model.WeatherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class WeatherInformationMapper {

    private static Logger LOG = LoggerFactory.getLogger("WeatherInformationMapper");

    public WeatherInfo map(WeatherForecast forecast, Integer zipcode, boolean isCached){
        WeatherInfo weatherInfo = new WeatherInfo();
        if(forecast != null){
            weatherInfo.setTemperature(forecast.getMain().getTemp());
            weatherInfo.setMaxTemperature(forecast.getMain().getTemp_max());
            weatherInfo.setMinTemperature(forecast.getMain().getTemp_min());
            weatherInfo.setDescription(forecast.getWeather().get(0).getDescription());
            weatherInfo.setHumidity(forecast.getMain().getHumidity());
            weatherInfo.setLocation(forecast.getName());
            weatherInfo.setZipcode(zipcode);
            weatherInfo.setCached(isCached);
            weatherInfo.setWindSpeed(forecast.getWind().getSpeed());
            LOG.debug("Weather Info Mapping completed");
        }
        return weatherInfo;
    }
}
