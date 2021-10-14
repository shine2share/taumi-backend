package com.shine2share.core.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ObjPublishListener {
    @EventListener
    @Async
    public void publishObjEventListener(ObjPublishEvent objPublishEvent) {
        String myName = objPublishEvent.getMyName();
        System.out.println("publish listener running: " + myName);
    }
}
