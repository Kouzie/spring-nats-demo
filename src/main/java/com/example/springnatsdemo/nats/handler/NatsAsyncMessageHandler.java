package com.example.springnatsdemo.nats.handler;

import io.nats.client.AsyncSubscription;
import io.nats.client.Connection;
import io.nats.client.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Slf4j
@Profile("sync")
@Component
@RequiredArgsConstructor
public class NatsAsyncMessageHandler {

    private final Connection natsConnection;
    private final MessageHandler messageHandler;
    private AsyncSubscription subscription;

    @Value("${nats.stream.topic}")
    private String topic;
    @Value("${nats.stream.queue}")
    private String queue;

    @PostConstruct
    private void init() {
        if (StringUtils.hasText(queue)) {
            subscription = natsConnection.subscribe(topic, queue, messageHandler);
        } else {
            subscription = natsConnection.subscribe(topic, messageHandler);
        }
    }

    @PreDestroy
    private void destroy() throws IOException {
        subscription.unsubscribe();
    }
}
