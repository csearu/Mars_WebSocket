package com.mars.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SensorData {
    private double oxygenLevel;
    private int co2Level;
    private double temperature;
    private double pressure;
    private int waterSupply;    
}
