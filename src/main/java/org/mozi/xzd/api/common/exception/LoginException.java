package org.mozi.xzd.api.common.exception;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/30 23:07
 */
public class LoginException extends RuntimeException {

    public LoginException() { }


    public LoginException(String message) {
        super(message);
    }


    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }


}
