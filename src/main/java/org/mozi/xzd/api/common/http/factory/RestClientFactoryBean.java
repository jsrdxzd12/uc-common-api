package org.mozi.xzd.api.common.http.factory;

import org.mozi.xzd.api.common.http.invocation.RestClientInvocationHandler;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/10 14:29
 */
public class RestClientFactoryBean <T> implements FactoryBean<T> {

    private Class<T> daoInterface;

    private RestClientInvocationHandler proxy;

    @Override
    public T getObject() throws Exception {
        return newInstance();
    }
    @Override
    public Class<?> getObjectType() {
        return daoInterface;
    }

    public RestClientInvocationHandler getProxy() {
        return proxy;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @SuppressWarnings("unchecked")
    private T newInstance() {
        proxy.setTarget(daoInterface);
        return (T) Proxy.newProxyInstance(daoInterface.getClassLoader(), new Class[] { daoInterface }, proxy);
    }

    public void setProxy(RestClientInvocationHandler proxy) {
        this.proxy = proxy;
    }

    public void setDaoInterface(Class<T> daoInterface) {
        this.daoInterface = daoInterface;
    }

}
