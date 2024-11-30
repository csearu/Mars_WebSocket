package com.mars.monitor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.mars.monitor.controller.SensorDataController;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SensorDataController sensorDataController;

    public WebSocketConfig(SensorDataController sensorDataController) {
        this.sensorDataController = sensorDataController;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(sensorDataController, "/sensor-data")
                .setAllowedOrigins("*"); // Allow all origins for testing
    }
}
