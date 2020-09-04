package org.mozi.xzd.api.common.exception;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/30 23:43
 */
public class TokenException extends RuntimeException {

    public TokenException() {
    }


    public TokenException(String message) {
        super(message);
    }


    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenException(Throwable cause) {
        super(cause);
    }
}
