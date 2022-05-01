import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.util.EncodingUtils;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EncryptorsTests {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    byte[] key;
    String password;
    String salt;

    @Before
    public void setup() {
        BytesKeyGenerator generator = KeyGenerators.secureRandom(16);
        key = generator.generateKey();

        char[] hex = Hex.encode(key);
        String hexString = String.valueOf(hex);

        password = hexString.substring(0, 16);
        salt = hexString.substring(16);

        logger.debug("key: {}", key);
        logger.debug("key length: {}", key.length);
        logger.debug("hex: {}", hex);
        logger.debug("hex length: {}", hex.length);
    }

    @Test
    public void generateKeyTest() {
        BytesKeyGenerator generator = KeyGenerators.secureRandom(16);
        logger.debug("hex: {}", String.valueOf(Hex.encode(generator.generateKey())));
        logger.debug("hex: {}", String.valueOf(Hex.encode(generator.generateKey())));
        logger.debug("hex: {}", String.valueOf(Hex.encode(generator.generateKey())));
    }

    @Test
    public void encryptTest() {
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

    @Test
    public void secretKeyTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey secretKey = keyFactory.generateSecret(new PBEKeySpec(password.toCharArray(), Hex.decode(salt), 1024, 256));
        logger.debug("format: {}", secretKey.getFormat());
        logger.debug("encoded: {}", secretKey.getEncoded());
        logger.debug("algorithm: {}", secretKey.getAlgorithm());
    }

    @Test
    public void implementsBytesEncryptorTest() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String password = "test1234";
        String salt = "01234567";

        BytesKeyGenerator ivGenerator = KeyGenerators.secureRandom(16);
        SecretKey secretKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(password.toCharArray(), Hex.decode(salt), 1024, 256));

        secretKey = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher encryptor = Cipher.getInstance(AesBytesEncryptor.CipherAlgorithm.GCM.toString());
        Cipher decryptor = Cipher.getInstance(AesBytesEncryptor.CipherAlgorithm.GCM.toString());

        String rawText = "테스트 문자열";

        byte[] iv = ivGenerator.generateKey();
        encryptor.init(Cipher.ENCRYPT_MODE, secretKey, AesBytesEncryptor.CipherAlgorithm.GCM.getParameterSpec(iv));
        byte[] encrypted = EncodingUtils.concatenate(iv, encryptor.doFinal(rawText.getBytes(StandardCharsets.UTF_8)));

        logger.debug("encrypted: {}", encrypted);

        decryptor.init(Cipher.DECRYPT_MODE, secretKey, AesBytesEncryptor.CipherAlgorithm.GCM.getParameterSpec(iv));
        iv = EncodingUtils.subArray(encrypted, 0, ivGenerator.getKeyLength());
        byte[] decrypted = decryptor.doFinal(EncodingUtils.subArray(encrypted, iv.length, encrypted.length));

        Assert.assertEquals(rawText, new String(decrypted, StandardCharsets.UTF_8));
    }
}
