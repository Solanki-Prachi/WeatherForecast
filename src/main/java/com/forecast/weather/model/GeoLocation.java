package com.forecast.weather.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GeoLocation {

    private String zip;
    private String name;
    private double lat;
    private double lon;
    private String country;

}
