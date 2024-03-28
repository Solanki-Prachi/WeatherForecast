package com.forecast.weather.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Sys {
    private int type;
    private int id;
    private String country;
    private long sunrise;
    private long sunset;

}
