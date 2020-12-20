package com.peiload.ridecare.anomaly.controller;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @SendTo("/topic/greetings")
    public String send(@Payload String message) {
        return message;
    }
}