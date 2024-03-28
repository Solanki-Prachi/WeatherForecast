package com.forecast.weather.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}
