package com.bto.receiver.controller;

import com.bto.receiver.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiverMessageController {


    private final RabbitTemplate rabbitTemplate;


    public ReceiverMessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }



    @GetMapping("/message")
    public  String receiverMessage(){
        Object message = rabbitTemplate.receiveAndConvert("kurs");
        if(message!=null){
            return "Udało sie pobrać wiadomość: " + message.toString() ;
        }else{
            return "Nie ma wiadomości do odczytu!: " + message.toString() ;
        }
    }

    //localhost:8080/message?message=kon

   @RabbitListener(queues = "kurs")
   public  void listenerMessages(Notification notification){

        System.out.println(notification.getEmail() + " " + notification.getTitle() + " " + notification.getBody());
  }

    @GetMapping("/notification")
    public ResponseEntity<Notification> receiveNotification(){
        Notification notification = rabbitTemplate.receiveAndConvert("kurs", ParameterizedTypeReference.forType(Notification.class));
        if(notification != null) {
            return ResponseEntity.ok(notification);
        }else {
            return ResponseEntity.noContent().build();
            }
        }
    }

