package org.example.webmvc.config.root;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "org.example.webmvc" , excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = {"org.example.webmvc.config.servlet.*", "org.example.webmvc.web"})
})
public class RootConfig {
}
