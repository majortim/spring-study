package org.example.springsecurity.config;

import org.example.springsecurity.crypto.AesEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class EncryptorConfig {
    @Value("classpath:/key/aes.key")
    private Resource resource;


    @Bean
    public AesEncryptor aesEncryptor() {
        SecretKeySpec key = null;

//        byte[] bytes = Files.readAllBytes(resource.getFile().toPath());

        return new AesEncryptor(key);
    }
}
