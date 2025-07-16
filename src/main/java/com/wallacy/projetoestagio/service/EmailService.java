package com.wallacy.projetoestagio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // A anotação @Autowired instrui o Spring a injetar a dependência aqui.
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("filmesr55@gmail.com"); // Recomendo usar a mesma conta configurada no properties

        // Com a injeção correta, mailSender não será mais nulo.
        mailSender.send(message);
    }
}