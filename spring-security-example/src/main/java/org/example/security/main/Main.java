package org.example.security.main;

import org.example.security.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.crypto.util.EncodingUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        PasswordEncoder passwordEncoder = ctx.getBean("passwordEncoder", PasswordEncoder.class);
        PasswordEncoder sCryptEncoder = ctx.getBean("sCryptEncoder", PasswordEncoder.class);
        PasswordEncoder bCryptEncoder = ctx.getBean("bCryptEncoder", PasswordEncoder.class);
        //PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

        String dpe = passwordEncoder.encode("test");
        String scrypt = sCryptEncoder.encode("test");
        String bcrypt = bCryptEncoder.encode("test");
        String sha3 = "";
        try {
            MessageDigest dg = MessageDigest.getInstance("SHA-256");
            byte[] value;
            byte[] secret = Utf8.encode("");
            byte[] retVal;
            BytesKeyGenerator saltGenerator = KeyGenerators.secureRandom();
            byte[] salt= saltGenerator.generateKey();

            value = EncodingUtils.concatenate(salt, secret, Utf8.encode("test"));
            for (int i = 0; i < 1024; i++) {
                value = dg.digest(value);
            }

            retVal = EncodingUtils.concatenate(value, "tt".getBytes());
            logger.debug("salt: {}, salt length: {}", salt, salt.length);
            //retVal = EncodingUtils.concatenate(salt, value);
            sha3 = new String(Hex.encode(retVal));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        logger.debug("dpe: {}", dpe);
        logger.debug("scrypt: {}", scrypt);
        logger.debug("bcrypt: {}", bcrypt);
        logger.debug("bcrypt: {}", bcrypt);
        logger.debug("sha3: {}", sha3);
        logger.debug("sha3 length: {}", sha3.getBytes().length);
        logger.debug("matches: {}", sCryptEncoder.matches("test", "{scrypt}" + scrypt));
        logger.debug("matches: {}", bCryptEncoder.matches("test", bcrypt));
        logger.debug("matches: {}", passwordEncoder.matches("test", dpe));
        logger.debug("matches: {}", passwordEncoder.matches("test", "{scrypt}" + scrypt));


        String idForEncode = "sha256";
        Map<String,PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());

        PasswordEncoder pe = new DelegatingPasswordEncoder(idForEncode, encoders);
        PasswordEncoder be = new BCryptPasswordEncoder();
        PasswordEncoder se = new SCryptPasswordEncoder();
        System.out.println("TEST TEST TEST");

        String rawPassword = "test1234";
        String encodedPassword = pe.encode(rawPassword);

        System.out.println(encodedPassword);
        System.out.println(pe.matches(rawPassword, encodedPassword));
        System.out.println(pe.matches(rawPassword, pe.encode("test12345")));
        System.out.println(pe.matches(rawPassword, "{bcrypt}" +  be.encode(rawPassword)));
        System.out.println(pe.matches(rawPassword, "{bcrypt}" +  be.encode("test12345!")));
        System.out.println(pe.matches(rawPassword, "{scrypt}" +  se.encode(rawPassword)));
        System.out.println(pe.matches(rawPassword, "{scrypt}" +  se.encode("test12345!")));

    }
}
