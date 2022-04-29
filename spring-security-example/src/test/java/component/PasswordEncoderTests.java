package component;

import org.example.security.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PasswordEncoderTests {

    @Qualifier("passwordEncoder")
    @Autowired
    PasswordEncoder passwordEncoder;
    @Qualifier("bCryptEncoder")
    @Autowired
    PasswordEncoder bCryptEncoder;

    private final static Logger LOGGER = LoggerFactory.getLogger(PasswordEncoderTests.class);
    private final static String TEST = "테스트 문자열";

    @Test
    public void delegatingPasswordEncoderTest() {
        String idForEncode = "pbkdf2";
        Map<String,PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());

        PasswordEncoder pe = new DelegatingPasswordEncoder(idForEncode, encoders);
        PasswordEncoder be = encoders.get("bcrypt");
        PasswordEncoder pbe = encoders.get("pbkdf2");

        String rawPassword = TEST;
        String encodedPassword = pe.encode(rawPassword);

        LOGGER.debug(encodedPassword);
        assertTrue(pe.matches(rawPassword, encodedPassword));
        assertTrue(pe.matches(rawPassword, "{bcrypt}" +  be.encode(rawPassword)));
        assertTrue(pe.matches(rawPassword, "{pbkdf2}" +  pbe.encode(rawPassword)));
    }

    @Test
    public void passwordEncoderTest() {
        String rawPassword = TEST;
        String pe = passwordEncoder.encode(rawPassword);
        assertTrue(passwordEncoder.matches(rawPassword, pe));
    }

    @Test
    public void bCryptEncoderTest() {
        String rawPassword = TEST;
        String bcrypt = bCryptEncoder.encode(rawPassword);
        assertTrue(bCryptEncoder.matches(rawPassword, bcrypt));
    }
}
