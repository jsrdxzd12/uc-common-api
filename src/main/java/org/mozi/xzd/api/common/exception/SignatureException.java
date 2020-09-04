package org.mozi.xzd.api.common.exception;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p>签名异常</p >
 * @since 2020/7/30 23:43
 */

public class SignatureException extends RuntimeException {

    public SignatureException() { }

    public SignatureException(String message) {
        super(message);
    }

    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignatureException(Throwable cause) {
        super(cause);
    }
}
