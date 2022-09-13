package com.example.springnatsdemo.websocket.config;

import com.example.springnatsdemo.nats.NatsComponent;
import com.example.springnatsdemo.dto.TopicMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final NatsComponent natsComponent;
    private final ObjectMapper objectMapper;
    private static Set<WebSocketSession> sessions = new ConcurrentHashMap().newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);
        log.info("client{} connect", session.getRemoteAddress());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("client{} handle message:{}", session.getRemoteAddress(), message.getPayload());
        try {
            TopicMessageDto topicMessage = objectMapper.readValue(message.getPayload(), TopicMessageDto.class);
            natsComponent.publish(topicMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
        log.info("client{} connect", session.getRemoteAddress());
    }
}
