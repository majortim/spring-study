package org.example.mail.component;

import org.example.mail.config.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(AppConfig.class)
class JavaMailSenderTests {

    @Autowired
    Environment environment;
    @Autowired
    JavaMailSender mailSender;

    @Test
    public void javaMailSender() {
        Assertions.assertDoesNotThrow(() -> {
            String from = environment.getRequiredProperty("mail.from");
            String to = environment.getRequiredProperty("mail.to");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("메일 테스트");
            message.setText("테스트");

            mailSender.send(message);
        });
    }
}