package com.mars.monitor.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.mars.monitor.model.SensorData;

@Service
public class SensorDataService {
    private final Random random = new Random();

    public SensorData generateSensorData(){
        return new SensorData(
            18.0 + (3.0 * random.nextDouble()),         // Oxygen Level (%)
            400 + random.nextInt(600),           // CO2 Level (ppm)
            -20.0 + (50.0 * random.nextDouble()),       // Temperature (°C)
            90.0 + (20.0 * random.nextDouble()),  // Pressure (kPa)
            random.nextInt(100)                  // Water Supply (liters)
        );
    }
    
}
