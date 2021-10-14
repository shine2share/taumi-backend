package com.shine2share.core.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ObjPublishSource {
    private final ApplicationEventPublisher publisher;

    public ObjPublishSource(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
    public void publish(String myName) {
        publisher.publishEvent(new ObjPublishEvent(this, myName));
    }
}
