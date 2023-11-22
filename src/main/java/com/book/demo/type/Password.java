package com.book.demo.type;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class Password {
    private String password;

    public Password() {}

    public Password(String userPassword) throws Exception 
    {
        this.password = encryptPassword(userPassword);
    }

    public final String getPassword(boolean plainPassword) throws Exception
    {
        if (!plainPassword) {
            return this.password;
        } else {
            return decryptPassword();
        }
    }  

    public final void changeNewPassword(String newPassword) throws Exception
    {
        this.password = encryptPassword(newPassword);
    }

    @Override
    public String toString() {
        return this.password;
    }

    private static final boolean areIncludeSpecialChar(String password) {
        Pattern pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    private static final SecretKey generateSecretKey() throws Exception 
    {
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        keyGenerator.init(168, secureRandom);
        String myKey = keyGenerator.toString();

        byte[] bytes = myKey.getBytes("UTF8");
        KeySpec keySpec = new DESedeKeySpec(bytes);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        return secretKey;
    }

    public final String encryptPassword() throws Exception
    {
        if (!areIncludeSpecialChar(password)) {
            throw new IllegalArgumentException("Password must contains special character.");
        } else {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey());
            byte[] encryptedPassword = cipher.doFinal(password.getBytes("UTF8"));
            return new String(Base64.getEncoder().encode(encryptedPassword));
        }
    }

    public static final String encryptPassword(String password) throws Exception 
    {
        if (!areIncludeSpecialChar(password)) {
            throw new IllegalArgumentException("Password must contains special character.");
        } else {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey());
            byte[] encryptedPassword = cipher.doFinal(password.getBytes("UTF8"));
            return new String(Base64.getEncoder().encode(encryptedPassword));
        }
    }

    public final String decryptPassword() throws Exception
    {
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, generateSecretKey());
        byte[] plainPassword = cipher.doFinal(Base64.getDecoder().decode(this.encryptPassword()));
        return new String(plainPassword);      
    }

    public static final String decryptPassword(String encryptedPassword) throws Exception
    {
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, generateSecretKey());
        byte[] plainPassword = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(plainPassword);
    }
}
