package com.hobbing.queue.presentation;

import com.hobbing.queue.application.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class KafkaProducerController {

    private final ProducerService producerService;


    @GetMapping("/send")
    public String sendMessage(@RequestParam("topic") String topic,
                              @RequestParam("key") String key,
                              @RequestParam("message") String message) {
        producerService.sendMessage(topic, key, message);
        return "Message sent to Kafka topic";
    }
}
