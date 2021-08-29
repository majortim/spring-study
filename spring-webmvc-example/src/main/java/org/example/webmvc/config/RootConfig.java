package org.example.webmvc.config;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@Import({DatabaseConfig.class, JdbcConfig.class})
@ComponentScan(basePackages = "org.example.webmvc" , excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, ControllerAdvice.class})
        , @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebConfig.class, ThymeleafConfig.class})
})
public class RootConfig {
}
