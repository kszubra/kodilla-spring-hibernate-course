package com.kodilla.course.springhibernatecourse.messaging.activemq;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessagingController {
    private JmsTemplate jmsTemplate;

    @GetMapping(path = "/process")
    public void processMessage(@RequestParam String message) {
        System.out.println("Message: " + message);

        jmsTemplate.convertAndSend("queue-test", message);
    }

}
