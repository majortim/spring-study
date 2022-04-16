package org.example.core.config;

import org.example.core.service.TestService;
import org.example.core.service.impl.TestServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "org.example.core.service")
@Configuration
public class AppConfig {

    @Bean
    public TestService superTestService() {
        return new TestServiceImpl("테스트");
    }
}
