package com.example.springnatsdemo.nats.handler;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import io.nats.client.SyncSubscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@EnableAsync
@Slf4j
@Profile("sync")
@Component
@RequiredArgsConstructor
public class NatsSyncMessageHandler {

    private final Connection natsConnection;
    private final MessageHandler messageHandler;
    private SyncSubscription subscription;

    @Value("${nats.stream.topic}")
    private String topic;
    @Value("${nats.stream.queue}")
    private String queue;
    private MessageHandlerThread thread;

    @PostConstruct
    private void init() {
        if (StringUtils.hasText(queue)) {
            subscription = natsConnection.subscribe(topic, queue);
        } else {
            subscription = natsConnection.subscribe(topic);
        }
        this.thread = new MessageHandlerThread();
        thread.start();
    }

    @PreDestroy
    private void destroy() throws IOException {
        subscription.unsubscribe();
        thread.interrupt();
    }


    public class MessageHandlerThread extends Thread {

        public void run() {
            System.out.println(this.getName() + ": New Thread is running...");
            while (true) {
                try {
                    Message message = subscription.nextMessage(1000);
                    if (message != null)
                        messageHandler.onMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
