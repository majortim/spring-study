import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EncryptorsTests {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void encryptTest() throws NoSuchAlgorithmException, InvalidKeySpecException {

        BytesKeyGenerator generator = KeyGenerators.secureRandom(16);
        byte[] key = generator.generateKey();
        char[] hex = Hex.encode(key);
        String hexString = String.valueOf(hex);

        logger.debug("key: {}", key);
        logger.debug("key length: {}", key.length);
        logger.debug("hex: {}", hex);
        logger.debug("hex length: {}", hex.length);

        String password = hexString.substring(0, 16);
        String salt = hexString.substring(16);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKey = keyFactory.generateSecret(new PBEKeySpec(password.toCharArray(), Hex.decode(salt), 1024, 256));
        logger.debug("format: {}", secretKey.getFormat());
        logger.debug("encoded: {}", secretKey.getEncoded());
        logger.debug("algorithm: {}", secretKey.getAlgorithm());

        TextEncryptor encryptor = Encryptors.delux(password, salt);
        String rawText = "This is text!";
        String cipherText = encryptor.encrypt(rawText);

        logger.debug("cipherText length: {}", cipherText.length());
        logger.debug("1234 CT length: {}", encryptor.encrypt("1234").length());
        logger.debug("test CT length: {}", encryptor.encrypt("test").length());

        String decryptText = Encryptors.delux(password, salt).decrypt(cipherText);
        logger.debug("decryptText: {}", decryptText);

        Assert.assertEquals(rawText, decryptText);
    }
}
