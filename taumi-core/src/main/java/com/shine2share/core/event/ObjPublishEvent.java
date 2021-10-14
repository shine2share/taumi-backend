package com.shine2share.core.event;

import org.springframework.context.ApplicationEvent;

public class ObjPublishEvent extends ApplicationEvent {
    private String myName;
    public ObjPublishEvent(Object source, String myName) {
        super(source);
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }
}
