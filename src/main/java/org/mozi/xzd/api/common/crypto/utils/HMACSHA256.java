package org.mozi.xzd.api.common.crypto.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;

/**
 * @author yuwenbo1
 * email: yuwenbo10@jd.com
 * copyright: © 2019  智能城市icity.jd.com ALL Right Reserved
 * @version v1.0.0
 * <p>
 * 微信验签工具
 * </p>
 * @since 2019年12月9日15:09:47
 */
@Slf4j
public class HMACSHA256 {
    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    public static String byteArrayToHexString(byte[] b) {

        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes).toUpperCase();
        } catch (Exception e) {
            log.error("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static String sha256_HMAC(Map<String, String> msgMap, String secret) {
        StringBuilder sb = new StringBuilder();
        msgMap.forEach((key, value) -> sb.append(key).append("=").append(value).append("&"));
        sb.append("key").append("=").append(secret);
        String s = sb.toString();
        log.info("待签名的串==>{}", s);
        return sha256_HMAC(s, secret);
    }

}