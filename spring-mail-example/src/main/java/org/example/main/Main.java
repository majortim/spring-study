package org.example.main;

import org.example.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class Main {
    public static void main(String[] args) {
        try (GenericApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {

            JavaMailSenderImpl sender = context.getBean(JavaMailSenderImpl.class);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(args[0]);
            message.setTo(args[1]);
            message.setSubject("메일 테스트");
            message.setText("테스트");

            sender.send(message);
        }
    }
}