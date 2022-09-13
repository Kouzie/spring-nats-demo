package com.example.springnatsdemo.nats.handler;

import io.nats.client.Message;
import io.nats.client.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomMessageHandler implements MessageHandler {

    @Override
    public void onMessage(Message msg) {
        log.info("subject:{}, payload:{}", msg.getSubject(), new String(msg.getData()));
    }
}
