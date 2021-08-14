package org.example.webmvc.web;

import org.example.webmvc.config.RootConfig;
import org.example.webmvc.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig({RootConfig.class, WebConfig.class})
public class PetControllerTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WebApplicationContext wac;

    @Test
    public void test() {
        logger.debug("wac: {}", wac);
    }
}
