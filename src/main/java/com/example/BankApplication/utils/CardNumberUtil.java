package com.example.BankApplication.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class CardNumberUtil {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "MySuperSecretKey"; // Замените на более безопасный ключ

    public static String generateCardNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    public static String encryptCardNumber(String cardNumber) throws Exception {
        SecretKey key = getKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(cardNumber.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decryptCardNumber(String encryptedCardNumber) throws Exception {
        SecretKey key = getKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(encryptedCardNumber);
        return new String(cipher.doFinal(decoded));
    }

    private static SecretKey getKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
    }
}