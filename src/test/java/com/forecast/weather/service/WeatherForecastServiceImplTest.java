package com.forecast.weather.service;

import com.forecast.weather.constants.WeatherForecastConstants;
import com.forecast.weather.mapper.WeatherInformationMapper;
import com.forecast.weather.model.*;
import com.forecast.weather.service.impl.WeatherForecastServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootTest
public class WeatherForecastServiceImplTest {

    @InjectMocks
    private WeatherForecastServiceImpl weatherForecastService;

    @Mock
    private WeatherInformationMapper weatherInformationMapper;
    @Mock
    private CacheManager cacheManagerMock;

    @Mock
    private CaffeineCache cache;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void getWeatherForecastFromCache() {
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setTemperature(300.0);
        weatherInfo.setLocation("XYZ");
        weatherInfo.setDescription("Cloudy");
        Mockito.when(cacheManagerMock.getCache(Mockito.anyString())).thenReturn(cache);
        Mockito.when(cache.get(123456, WeatherInfo.class)).thenReturn(weatherInfo);
        WeatherInfo weather = weatherForecastService.getWeatherInformation(123456);
        Assertions.assertEquals(true, weather.isCached());
        Assertions.assertEquals(300.0, weather.getTemperature());
    }

    @Test
    public void getWeatherForecast() {

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setTemperature(300.0);
        weatherInfo.setLocation("XYZ");
        weatherInfo.setDescription("Cloudy");

        Mockito.when(cacheManagerMock.getCache(Mockito.anyString())).thenReturn(cache);
        Mockito.when(cache.get(123456, WeatherInfo.class)).thenReturn(null);
        GeoLocation geoLocation = new GeoLocation("123456", "XYZ", 18.5685, 73.9158, "IN");
        ResponseEntity<GeoLocation> mockResponseEntity = new ResponseEntity<>(geoLocation, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity("locationurl", GeoLocation.class)).thenReturn(mockResponseEntity);

        WeatherForecast forecast = new WeatherForecast();
        forecast.setCoord(new Coord(123.45, 345.54));
        forecast.setWeather(Arrays.asList(new Weather(123, "Haze", "haze", "50nn")));
        ResponseEntity<WeatherForecast> mockWeatherForecast = new ResponseEntity<>(forecast, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity("WeatherURL", WeatherForecast.class)).thenReturn(mockWeatherForecast);

        Mockito.when(weatherInformationMapper.map(forecast, 123456, false)).thenReturn(weatherInfo);

        WeatherInfo weather = weatherForecastService.getWeatherInformation(123456);

        Assertions.assertEquals(false, weather.isCached());
        Assertions.assertEquals(300.0, weather.getTemperature());
    }
}
