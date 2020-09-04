package org.mozi.xzd.api.common.http.processor;

import org.mozi.xzd.api.common.http.annotation.RestClient;
import org.mozi.xzd.api.common.http.invocation.RestClientInvocationHandler;
import org.mozi.xzd.api.common.http.scan.RestClientMapperScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/10 15:05
 */
@Slf4j
public class RestClientBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

    private Class<? extends Annotation> restClientAnnotation = RestClient.class;

    private String componentRepositoryPackages;


    public void setComponentRepositoryPackages(String componentRepositoryPackages ){
        this.componentRepositoryPackages=componentRepositoryPackages;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        GenericBeanDefinition restProxyDefinition = new GenericBeanDefinition();
        restProxyDefinition.setBeanClass(RestClientInvocationHandler.class);
        beanDefinitionRegistry.registerBeanDefinition("interfaceDaoHandler", restProxyDefinition);

        RestClientMapperScanner restClientMapperScanner=new RestClientMapperScanner(beanDefinitionRegistry,restClientAnnotation);
        restClientMapperScanner.scan(StringUtils.tokenizeToStringArray(this.componentRepositoryPackages,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}

