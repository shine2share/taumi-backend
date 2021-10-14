package com.shine2share.system.controller;

import com.shine2share.core.event.ObjPublishSource;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {
    private final ObjPublishSource publishSource;

    public EventController(ObjPublishSource publishSource) {
        this.publishSource = publishSource;
    }

    @GetMapping("/test")
    @ApiOperation("API to test spring event")
    public String testEvent(@RequestParam @ApiParam("what is your name") String myName) {
        System.out.println("event controller starting....");
        // publish event
        publishSource.publish(myName);
        System.out.println("event controller ending....");
        return "success";
    }
}
