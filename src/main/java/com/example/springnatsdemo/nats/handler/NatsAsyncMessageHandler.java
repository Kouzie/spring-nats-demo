package com.example.springnatsdemo.nats.handler;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.MessageHandler;
import io.nats.client.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Profile("async")
@Component
@RequiredArgsConstructor
public class NatsAsyncMessageHandler {

    private final Connection natsConnection;
    private final MessageHandler messageHandler;

    @Value("${nats.stream.topic}")
    private String topic;
    @Value("${nats.stream.queue}")
    private String queue;
    private Dispatcher dispatcher;

    @PostConstruct
    private void init() {
        // CountDownLatch latch = new CountDownLatch(1);

        dispatcher = natsConnection.createDispatcher(messageHandler);
        if (StringUtils.hasText(queue)) {
            dispatcher.subscribe(topic, queue);
        } else {
            dispatcher.subscribe(topic);
        }
    }
}
