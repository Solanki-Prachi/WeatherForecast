package com.forecast.weather.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Wind {
    private double speed;
    private int deg;
}
