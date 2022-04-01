import org.example.springsecurity.config.EncryptorConfig;
import org.example.springsecurity.crypto.AesEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = EncryptorConfig.class)
public class EncryptorsTests {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AesEncryptor aesEncryptor;

    @Test
    public void encryptTest() {
        String salt = KeyGenerators.string().generateKey();
        String password = "myPassword";

        logger.debug("password: {}", password);
        logger.debug("salt: {}", salt);

        TextEncryptor encryptor = Encryptors.delux(password, salt);

        logger.debug("text: {}", encryptor.encrypt("test"));
    }

    @Test
    public void decryptTest() {
//        String password = encoder.encode("myPassword");
//        String salt = KeyGenerators.string().generateKey();
//
        TextEncryptor encryptor = Encryptors.delux("myPassword", "2d88cdd9ed91466a");

        logger.debug("text: {}", encryptor.decrypt("199be7bc06e2303b2607036bfc812d08206c19d84e2c0a426ad451206b59fc40446d3050"));
    }
}
