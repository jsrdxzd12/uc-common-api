package org.mozi.xzd.api.common.http.auth;

import org.mozi.xzd.api.common.exception.SignatureException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/5 14:19
 */
public interface SignAuthCheck {

   default void validSign(HttpServletRequest request,Object[] param) throws SignatureException, IOException {}
}
