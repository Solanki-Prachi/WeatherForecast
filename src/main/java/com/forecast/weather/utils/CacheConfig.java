package com.forecast.weather.utils;

import com.forecast.weather.constants.WeatherForecastConstants;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    @Value("${caching.spring.interval:30}")
    private int duration;
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(WeatherForecastConstants.WEATHER_FORECAST_CACHE);
        cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(duration, TimeUnit.MINUTES));
        return cacheManager;

    }
}
