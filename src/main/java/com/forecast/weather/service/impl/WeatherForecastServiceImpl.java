package com.forecast.weather.service.impl;

import com.forecast.weather.constants.WeatherForecastConstants;
import com.forecast.weather.exception.InvalidZipcodeException;
import com.forecast.weather.mapper.WeatherInformationMapper;
import com.forecast.weather.model.GeoLocation;
import com.forecast.weather.model.WeatherForecast;
import com.forecast.weather.model.WeatherInfo;
import com.forecast.weather.service.WeatherForecastService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherForecastServiceImpl implements WeatherForecastService {

    private static Logger LOG = LoggerFactory.getLogger("WeatherForecastServiceImpl");

    @Autowired
    private WeatherInformationMapper weatherInformationMapper;

    @Autowired
    private CacheManager cacheManager;

    private RestTemplate restTemplate;

    public WeatherForecastServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * API to fetch weather forecast for given zipcode
     *
     * @param zipcode
     * @return WeatherInfo
     */
    @Override
    public WeatherInfo getWeatherInformation(Integer zipcode) {
        WeatherInfo weatherInfo = cacheManager.getCache(WeatherForecastConstants.WEATHER_FORECAST_CACHE).get(zipcode, WeatherInfo.class);
        if (weatherInfo != null) {
            weatherInfo.setCached(true);
            LOG.info("Weather Information found in cache.");
            return weatherInfo;
        }
        GeoLocation geoLocation = getGeoLocation(zipcode);
        if (geoLocation == null) {
            LOG.error("Zipcode Not Found.");
            throw new InvalidZipcodeException();
        }
        WeatherForecast forecast = getWeatherForecast(geoLocation);
        LOG.info("Weather Information retrived.");
        weatherInfo = weatherInformationMapper.map(forecast, zipcode, false);
        cacheManager.getCache(WeatherForecastConstants.WEATHER_FORECAST_CACHE).putIfAbsent(zipcode, weatherInfo);
        return weatherInfo;

    }

    private WeatherForecast getWeatherForecast(GeoLocation geoLocation) {
        ResponseEntity<WeatherForecast> response = null;
        try {
            response = restTemplate.getForEntity(getWeatherUrl(geoLocation), WeatherForecast.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                LOG.error("Zipcode Not Found.");
                throw new InvalidZipcodeException();
            }
        } catch (Exception e) {
            LOG.error("Zipcode Not Found.");
            throw new InvalidZipcodeException();
        }
        return response.getBody();
    }

    private static String getWeatherUrl(GeoLocation geoLocation) {
        StringBuilder weatherUrl = new StringBuilder();
        weatherUrl.append(WeatherForecastConstants.WEATHER_ENDPOINT);
        weatherUrl.append("lat=");
        weatherUrl.append(geoLocation.getLat());
        weatherUrl.append("&lon=");
        weatherUrl.append(geoLocation.getLon());
        weatherUrl.append("&appid=");
        weatherUrl.append(WeatherForecastConstants.OPEN_WEATHER_API_KEY);
        weatherUrl.append("&dt=");
        weatherUrl.append(System.currentTimeMillis());
        return weatherUrl.toString();
    }

    private GeoLocation getGeoLocation(Integer zipcode) {
        ResponseEntity<GeoLocation> response = null;
        try {
            response = restTemplate.getForEntity(getLocationUrl(zipcode), GeoLocation.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                LOG.error("Zipcode Not Found.");
                throw new InvalidZipcodeException();
            }
        } catch (Exception e) {
            LOG.error("Zipcode Not Found.");
            throw new InvalidZipcodeException();
        }

        return response.getBody();
    }

    private static String getLocationUrl(Integer zipcode) {
        StringBuilder locationUrl = new StringBuilder();
        locationUrl.append(WeatherForecastConstants.COORDINATES_ENDPOINT);
        locationUrl.append("zip=");
        locationUrl.append(zipcode);
        locationUrl.append(",in&appid=");
        locationUrl.append(WeatherForecastConstants.OPEN_WEATHER_API_KEY);
        return locationUrl.toString();
    }
}
