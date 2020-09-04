package org.mozi.xzd.api.common.crypto.factory.strategy;

import org.mozi.xzd.api.common.exception.SignatureException;
import org.mozi.xzd.api.common.crypto.utils.HMACSHA256;
import org.mozi.xzd.api.common.crypto.factory.AkskStrategy;
import org.mozi.xzd.api.common.crypto.factory.GenerateSignatureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/5 22:25
 */
@Component("HMAC_SHA256")
@Slf4j
public class HmacSha256Strategy implements AkskStrategy {

    public HmacSha256Strategy(){
        log.info("xiaoyuyu....................................");
    }

    @Override
    public String encode(String... data) {
        GenerateSignatureUtils.notEmpty(data);
        return HMACSHA256.sha256_HMAC(data[0],data[1]);
    }

    @Override
    public boolean matches(String signature, String originSign) throws SignatureException {
        return GenerateSignatureUtils.matches(signature,originSign);
    }
}
