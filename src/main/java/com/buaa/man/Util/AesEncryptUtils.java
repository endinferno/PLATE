package com.buaa.man.Util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static javax.crypto.Cipher.*;

public class AesEncryptUtils {
    private static final String KEY = "abcdef0123456789";
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     * @param content 加密的字符串
     * @return String
     */
    public static String encrypt(String content) {
        byte[] b;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = getInstance(ALGORITHMSTR);
            cipher.init(ENCRYPT_MODE, new SecretKeySpec(KEY.getBytes(), "AES"));
            b = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException
            | InvalidKeyException | BadPaddingException e) {
            b = content.getBytes();
        }
        return Base64.encodeBase64String(b);
    }
}
