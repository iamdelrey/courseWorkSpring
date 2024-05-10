package org.example.gallery.service;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String content) {
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("Адрес электронной почты не может быть null или пустым.");
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Обращение в поддержку");
            message.setText(content);
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}