package org.mozi.xzd.api.common.jwt;

import org.mozi.xzd.api.common.constant.SecurityConstants;
import org.mozi.xzd.api.common.exception.TokenException;
import org.mozi.xzd.api.common.json.JsonMapper;
import org.mozi.xzd.api.common.user.domain.UserInfoBo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/30 23:11
 */

@Slf4j
public class JwtTokenUtils {


    // 过期时间是3600秒，既是3个小时
    private static final long EXPIRATION = 3600*3;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    public static String generateToken(String subject, UserInfoBo user, boolean isRememberMe) {

        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;

        return generateToken(subject, user, expiration);
    }

    public static String generateToken(String subject, UserInfoBo user, long expiration) {

        return SecurityConstants.TOKEN_PREFIX + Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE).setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .claim(SecurityConstants.ROLE_CLAIMS, user).setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setSubject(subject).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)).compact();
    }


    /**
     * 获取用户角色
     */
    @SuppressWarnings("unchecked")
    public static UserInfoBo getJwtUser(String token) throws TokenException {
        Claims claims = getTokenBody(token);
        if (ObjectUtils.isEmpty(claims)){
            return null;
        }
        try {
            Object object = claims.get(SecurityConstants.ROLE_CLAIMS);
            if (ObjectUtils.isEmpty(object)) {
                return null;
            }
            UserInfoBo user= JsonMapper.jsonToObject( JsonMapper.objectToJson(object), UserInfoBo.class);

            return user ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取
     */
    private static Claims getTokenBody(String token) throws TokenException {
        try {
            return Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes()))
                    .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody();

        } catch (ExpiredJwtException exception) {

            log.error("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());// 超时
            throw new TokenException("token已过期，请重新登录.");
        } catch (UnsupportedJwtException exception) {
            log.error("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());// 不支持
            throw new TokenException("token不支持.");
        } catch (MalformedJwtException exception) {
            log.error("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());// 无效
            throw new TokenException("token无效.");
        } catch (SecurityException exception) {
            log.error("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());// 无效签名
            throw new TokenException("token无效签名.");
        } catch (IllegalArgumentException exception) {
            log.error("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());// 解析空或JWT为空的请求
            throw new TokenException("token解析空或值为空的请求.");
        }

    }
}
