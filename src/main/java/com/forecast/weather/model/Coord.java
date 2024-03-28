package com.forecast.weather.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Coord {
    private double lon;
    private double lat;
}
