package org.mozi.xzd.api.common.crypto.factory;

import org.mozi.xzd.api.common.exception.SignatureException;
import org.springframework.util.Assert;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/5 22:34
 */
public class GenerateSignatureUtils {

    public static void notEmpty(String... data){
        Assert.notEmpty(data,"加密参数不能为空！");
    }

    public static boolean matches(String signature, String originSign) throws SignatureException {
        if (!signature.equals(originSign)) {
            throw new SignatureException("非法的验签密钥！");
        }
        return true;
    }
}
