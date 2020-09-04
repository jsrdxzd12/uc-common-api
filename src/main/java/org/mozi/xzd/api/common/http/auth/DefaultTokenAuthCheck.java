package org.mozi.xzd.api.common.http.auth;

import org.mozi.xzd.api.common.exception.TokenException;
import org.mozi.xzd.api.common.jwt.JwtTokenUtils;
import org.mozi.xzd.api.common.profile.properties.ApiClientSignatureProperties;
import org.mozi.xzd.api.common.user.domain.UserInfoBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/5 15:23
 */
@Slf4j
//@Component
public class DefaultTokenAuthCheck implements TokenAuthCheck{

    @Resource
    private ApiClientSignatureProperties apiSignatureProperties ;

    @Override
    public UserInfoBo validToken(HttpServletRequest request) throws TokenException {
        return checkToken(request);
    }

    private UserInfoBo checkToken(HttpServletRequest request) throws TokenException {

        String bearerToken = request.getHeader(apiSignatureProperties.getBearerToken());
        if (!StringUtils.hasLength(bearerToken)) {
            throw new TokenException("非法的数据访问");
        }
        UserInfoBo jwtUser = JwtTokenUtils.getJwtUser(bearerToken);
        if (Objects.isNull(jwtUser)) {
            throw new TokenException("token已过期，请重新授权！");
        }
        return jwtUser;
    }
}
