package org.mozi.xzd.api.common.http.handler;

import org.mozi.xzd.api.common.http.RestClient;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/10 14:21
 */
public class RestClientHandler implements InvocationHandler {

    @Resource
    private RestClient restClient;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}