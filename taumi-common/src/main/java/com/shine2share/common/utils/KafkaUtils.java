package com.shine2share.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaUtils {
    private static final Logger logger = LoggerFactory.getLogger(KafkaUtils.class);
    //@Qualifier("kafkaTransTemplate")
    private final KafkaTemplate<String, String> kafkaTransTemplate;
    //@Qualifier("kafkaTemplate")
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaUtils(KafkaTemplate<String, String> kafkaTransTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTransTemplate = kafkaTransTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        sendMessage(topic, message, true);
    }
    public void sendMessage(String topic, String message, boolean isTrans) {
        ListenableFutureCallback<SendResult<String, String>> callback = new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.info("can not send message=[" + message + "] due to: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                logger.info("sent message=[" + message + "] with offset=[" + stringStringSendResult.getRecordMetadata().offset() + "]");
            }
        };
        ListenableFuture<SendResult<String, String>> future;
        if (isTrans) {
            future = kafkaTransTemplate.send(topic, message);
        } else {
            future = kafkaTemplate.send(topic, message);
        }
        future.addCallback(callback);
    }
}
