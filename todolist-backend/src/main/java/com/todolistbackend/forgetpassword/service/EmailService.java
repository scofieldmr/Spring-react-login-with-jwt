package com.todolistbackend.forgetpassword.service;

import com.todolistbackend.forgetpassword.dto.MailBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(MailBody mailBody) {
       SimpleMailMessage message = new SimpleMailMessage();
       message.setFrom(mailFrom);
       message.setTo(mailBody.mailTo());
       message.setSubject(mailBody.subject());
       message.setText(mailBody.body());

       mailSender.send(message);

    }
}
