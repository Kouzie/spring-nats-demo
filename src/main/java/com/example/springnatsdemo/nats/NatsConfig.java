package com.example.springnatsdemo.nats;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
public class NatsConfig {

    @Value("${nats.stream.uri}")
    private String uri;

    @Bean
    Connection initConnection() throws IOException {
        Options options = new Options.Builder()
                .errorCb(ex -> log.error("Connection Exception: ", ex))
                .disconnectedCb(event -> log.error("Channel disconnected: {}", event.getConnection()))
                .reconnectedCb(event -> log.error("Reconnected to server: {}", event.getConnection()))
                .build();
        return Nats.connect(uri, options);
    }
}
