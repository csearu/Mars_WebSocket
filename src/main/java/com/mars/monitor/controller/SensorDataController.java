package com.mars.monitor.controller;

import com.mars.monitor.model.SensorData;
import com.mars.monitor.service.SensorDataService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class SensorDataController extends TextWebSocketHandler {

    private final SensorDataService sensorDataService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SensorDataController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
        startSendingSensorData();
    }

    private void startSendingSensorData() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            SensorData sensorData = sensorDataService.generateSensorData();
            try {
                // Broadcast to all connected clients
                for (WebSocketSession session : sessions) {
                    if (session.isOpen()) {
                        String message = objectMapper.writeValueAsString(sensorData);
                        session.sendMessage(new TextMessage(message));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS); // Update every second
    }

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}
