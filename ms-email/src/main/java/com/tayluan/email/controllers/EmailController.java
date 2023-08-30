package com.tayluan.email.controllers;

import com.tayluan.email.dtos.EmailDto;
import com.tayluan.email.models.EmailModel;
import com.tayluan.email.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class EmailController {
    @Autowired
    EmailService emailService;

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@Valid @RequestBody EmailDto emailDto){
       rabbitTemplate.convertAndSend(queue,emailDto);
       return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/emails")
    public ResponseEntity<List<EmailModel>> findAllEmails(){
        return ResponseEntity.status(HttpStatus.OK).body(emailService.findAllEmails());
    }

}
