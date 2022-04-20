package org.example.mail.main;

import org.example.mail.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class Main {
    public static void main(String[] args) {
        try (GenericApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {

            Environment environment = context.getEnvironment();
            JavaMailSenderImpl sender = context.getBean(JavaMailSenderImpl.class);

            String from = environment.getRequiredProperty("mail.from");
            String to = environment.getRequiredProperty("mail.to");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("메일 테스트");
            message.setText("테스트");

            sender.send(message);
        }
    }
}