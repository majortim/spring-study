package org.example.springsecurity.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesEncryptor {
    private final SecretKeySpec key;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public AesEncryptor(SecretKeySpec key) {
        this.key = key;
    }

    public String encrypt(String input, IvParameterSpec iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder()
                    .encodeToString(cipherText);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    public String decrypt(String cipherText, IvParameterSpec iv) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                    .decode(cipherText));
            return new String(plainText);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
