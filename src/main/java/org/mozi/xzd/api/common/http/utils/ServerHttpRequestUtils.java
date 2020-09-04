package org.mozi.xzd.api.common.http.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/29 14:58
 */
public class ServerHttpRequestUtils {

    /**
     * 获取 WebFlux ServerHttpRequest 用户参数值
     * @param serverHttpRequest WebFlux request
     * @param key 参数值
     */
    public static String getHttpHeaderParam(ServerHttpRequest serverHttpRequest, String key) {
        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
        String param = httpHeaders.getFirst(key);
        if (StringUtils.isEmpty(param)) {
            param = serverHttpRequest.getQueryParams().getFirst(key);
        }
        return param;
    }


    /**
     * 获取 HttpServletRequest 对象
     */
    public static  HttpServletRequest getHttpServletRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        return  attributes.getRequest();
    }

    /**
     * 获取 HttpServletRequest attribute 用户参数值
     * @param attribute    Attribute
     * @param <T> 自动识别对象并转换
     * @return
     */
    public static  <T> T getAttrEntity(String attribute){
        return (T) getHttpServletRequest().getAttribute(attribute);
    }

}