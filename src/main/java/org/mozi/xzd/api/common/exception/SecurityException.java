package org.mozi.xzd.api.common.exception;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/30 23:07
 */
public class SecurityException extends RuntimeException {

    public SecurityException() {
    }


    public SecurityException(String message) {
        super(message);
    }


    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityException(Throwable cause) {
        super(cause);
    }


}
