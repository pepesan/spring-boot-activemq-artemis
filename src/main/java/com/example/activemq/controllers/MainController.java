package com.example.activemq.controllers;

import com.example.activemq.dtos.CustomMessage;
import com.example.activemq.services.SseService;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class MainController {

    private final JmsTemplate jmsTemplate;
    private final SseService sseService;

    @Autowired
    public MainController(JmsTemplate jmsTemplate, SseService sseService) {
        this.jmsTemplate = jmsTemplate;
        this.sseService = sseService;
    }
    @GetMapping()
    public String main(){
        return "Hola";
    }

    public List<String> receiveAllFromQueue(String destination) {
        return jmsTemplate.execute(session -> {
            try (final MessageConsumer consumer = session.createConsumer(session.createQueue(destination))) {
                List<String> messages = new ArrayList<>();
                Message message;
                while ((message = consumer.receiveNoWait()) != null) {
                    messages.add(message.getBody(String.class));
                }
                return messages;
            }
        }, true);
    }

    @GetMapping("/queues/{queue}")
    public String getQueues(@PathVariable("queue") String queue){
        List<String> listado = receiveAllFromQueue(queue);
        log.info(listado.toString());
        return listado.toString();
    }
    @PostMapping("/queues/send")
    @ResponseBody
    public void sendMessage(@RequestBody CustomMessage message) {
        log.info("message [{}] sent to queue [{}]",message.getContent(),message.getQueue());
        jmsTemplate.convertAndSend(message.getQueue(), message.getContent());
        sseService.notifyListeners(message.getQueue(),message.getContent());
    }
}
