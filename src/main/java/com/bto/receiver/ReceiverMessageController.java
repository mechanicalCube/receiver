package com.bto.receiver;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
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


}
