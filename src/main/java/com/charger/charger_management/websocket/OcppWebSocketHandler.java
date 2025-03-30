package com.charger.charger_management.websocket;

import com.charger.charger_management.service.ChargerService;
import com.charger.charger_management.service.TransactionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;


import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OcppWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChargerService chargerService;

    @Autowired
    private TransactionService transactionService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private ConcurrentHashMap<String, WebSocketSession> chargerSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Charger connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        JsonNode jsonMessage = objectMapper.readTree(message.getPayload());
        String action = jsonMessage.get(0).asText();

        switch (action) {
            case "BootNotification":
                handleBootNotification(session, jsonMessage);
                break;
            case "Heartbeat":
                handleHeartbeat(session, jsonMessage);
                break;
            case "StatusNotification":
                handleStatusNotification(session, jsonMessage);
                break;
            case "StartTransaction":
                handleStartTransaction(session, jsonMessage);
                break;
            case "StopTransaction":
                handleStopTransaction(session, jsonMessage);
                break;
            default:
                System.out.println("Unknown message type: " + action);
        }
    }

    private void handleBootNotification(WebSocketSession session, JsonNode message) throws IOException {
        String chargerId = message.get(1).asText();
        chargerSessions.put(chargerId, session);
        chargerService.updateChargerStatus(chargerId, "Available");

        sendResponse(session, message.get(2).asText(), "Accepted");
    }

    private void handleHeartbeat(WebSocketSession session, JsonNode message) throws IOException {
        String chargerId = message.get(1).asText();
        chargerService.updateLastHeartbeat(chargerId);

        sendResponse(session, message.get(2).asText(), "Pong");
    }

    private void handleStatusNotification(WebSocketSession session, JsonNode message) throws IOException {
        String chargerId = message.get(1).asText();
        String status = message.get(2).asText();
        chargerService.updateChargerStatus(chargerId, status);

        sendResponse(session, message.get(3).asText(), "Accepted");
    }

    private void handleStartTransaction(WebSocketSession session, JsonNode message) throws IOException {
        String chargerId = message.get(1).asText();
        String transactionId = message.get(2).asText();
        transactionService.startTransaction(chargerId, transactionId);

        sendResponse(session, message.get(3).asText(), "Accepted");
    }

    private void handleStopTransaction(WebSocketSession session, JsonNode message) throws IOException {
        String transactionId = message.get(2).asText();
        transactionService.stopTransaction(transactionId);

        sendResponse(session, message.get(3).asText(), "Accepted");
    }

    private void sendResponse(WebSocketSession session, String messageId, String status) throws IOException {
        String response = "[3, \"" + messageId + "\", {\"status\": \"" + status + "\"}]";
        session.sendMessage(new TextMessage(response));
    }
}
