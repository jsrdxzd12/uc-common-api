package org.mozi.xzd.api.common.crypto.factory.strategy;

import org.mozi.xzd.api.common.exception.SignatureException;
import org.mozi.xzd.api.common.crypto.factory.AkskStrategy;
import org.mozi.xzd.api.common.crypto.factory.GenerateSignatureUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/5 22:25
 */
@Component("SHA256")
public class Sha256Strategy implements AkskStrategy {

    @Override
    public String encode(String... data) {
        GenerateSignatureUtils.notEmpty(data);
        return new String(DigestUtils.sha256(data[0]+data[1]));
    }

    @Override
    public boolean matches(String signature, String originSign) throws SignatureException {
        return GenerateSignatureUtils.matches(signature, originSign);
    }

}
