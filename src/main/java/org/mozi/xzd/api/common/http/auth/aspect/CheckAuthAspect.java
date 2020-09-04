package org.mozi.xzd.api.common.http.auth.aspect;

import org.mozi.xzd.api.common.http.utils.ServerHttpRequestUtils;
import org.mozi.xzd.api.common.http.auth.SignAuthCheck;
import org.mozi.xzd.api.common.http.auth.TokenAuthCheck;
import org.mozi.xzd.api.common.json.JsonMapper;
import org.mozi.xzd.api.common.user.domain.UserInfoBo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/3 23:42
 */

@Aspect
@Component
@Slf4j
public class CheckAuthAspect {

    public CheckAuthAspect() {
        log.info("ApiConfig");
    }

    @Resource
    private SignAuthCheck signAuthCheck;


    @Resource
    private TokenAuthCheck tokenAuthCheck;

    @Around("@annotation(org.mozi.xzd.api.common.http.auth.aspect.CheckAuthSignature)")
    public Object checkAuthSignature(ProceedingJoinPoint point) throws Throwable {

        signAuthCheck.validSign(ServerHttpRequestUtils.getHttpServletRequest(), point.getArgs());
        Object result = point.proceed();

        log.info("请求结束===返回值:" + JsonMapper.objectToJson(result));
        return  result;
    }


    @Around("@annotation(org.mozi.xzd.api.common.http.auth.aspect.CheckAuthToken)")
    public Object checkAuthToken(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request=ServerHttpRequestUtils.getHttpServletRequest();
        UserInfoBo jwtUser = tokenAuthCheck.validToken(request);
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        CheckAuthToken annotation = method.getAnnotation(CheckAuthToken.class);

        String[] name= annotation.value();

        if(!ObjectUtils.isEmpty(name) && !Objects.equals(jwtUser.getRoleList(),name)){
            throw new SecurityException("用户无权访问");
        }

        request.setAttribute("jwtUser",jwtUser);
        return point.proceed();
    }
}
