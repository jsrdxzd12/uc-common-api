package org.mozi.xzd.api.common.crypto.factory;

import org.mozi.xzd.api.common.exception.SignatureException;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/5 22:19
 */
public interface AkskStrategy {

    String encode(String... data);


    default boolean matches(String signature, String originSign) throws SignatureException {
        return false;
    }

    /**
     * RSA特殊算法比对验签
     * @param target
     * @param signature
     * @return
     * @throws SignatureException
     */
    default boolean matches(Object target,String... signature) throws SignatureException {return false;}
}
