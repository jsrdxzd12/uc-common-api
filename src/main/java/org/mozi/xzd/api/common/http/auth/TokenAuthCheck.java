package org.mozi.xzd.api.common.http.auth;

import org.mozi.xzd.api.common.exception.TokenException;
import org.mozi.xzd.api.common.user.domain.UserInfoBo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/5 15:23
 */
public interface TokenAuthCheck {

    UserInfoBo validToken(HttpServletRequest request) throws TokenException;
}