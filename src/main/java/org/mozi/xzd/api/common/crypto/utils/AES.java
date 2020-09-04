package org.mozi.xzd.api.common.crypto.utils;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author yuwenbo1
 * email: yuwenbo10@jd.com
 * copyright: © 2020 智能城市icity.jd.com ALL Right Reserved
 * @version v1.0.0
 * <p>
 * </p >
 * @since 2020/4/1 14:45
 */
@Slf4j
public class AES {

    private static final String KEY_ALGORITHMS = "AES";

    /**
     * AES的密钥长度
     */
    private static final Integer SECRET_KEY_LENGTHS = 128;
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String DEFAULT_CIPHER_ALGORITHMS = "AES/ECB/PKCS5Padding";

    private static SecretKeySpec getSecretKey(final String password) throws NoSuchAlgorithmException {
        //生成指定算法密钥的生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHMS);
        keyGenerator.init(SECRET_KEY_LENGTHS, new SecureRandom(password.getBytes()));
        //生成密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //转换成AES的密钥
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHMS);
    }


    /**
     * 加密
     *
     * @param sSrc
     * @param privateKey
     * @return
     */
    public static String encrypt(String sSrc, String privateKey) {
        if (sSrc == null || "".equals(sSrc)) {
            return null;
        }
        byte[] encrypted = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHMS);
            byte[] raw = privateKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e);
        }
        return Base64.encodeBase64String(encrypted);
    }

    /**
     * 解密
     *
     * @param sSrc
     * @param privateKey
     * @return
     */
    public static String decrypt(String sSrc, String privateKey) {
        if (sSrc == null || "".equals(sSrc)) {
            return null;
        }
        try {
            byte[] raw = privateKey.getBytes(StandardCharsets.US_ASCII);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHMS);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.decodeBase64(sSrc.getBytes(StandardCharsets.UTF_8));
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            log.error(Throwables.getStackTraceAsString(ex));
            throw new RuntimeException(ex);
        }
    }
}