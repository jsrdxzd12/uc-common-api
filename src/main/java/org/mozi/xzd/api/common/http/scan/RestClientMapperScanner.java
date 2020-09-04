package org.mozi.xzd.api.common.http.scan;

import org.mozi.xzd.api.common.http.factory.RestClientFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/10 14:26
 */

@Slf4j
public class RestClientMapperScanner extends ClassPathBeanDefinitionScanner {

    public RestClientMapperScanner(BeanDefinitionRegistry registry,Class<? extends Annotation> annotation) {
        super(registry,false);
        addIncludeFilter(new AnnotationTypeFilter(annotation));
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages)   {

        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            log.warn("No Dao interface was found in '" + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        }


        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.getPropertyValues().add("proxy", getRegistry().getBeanDefinition("interfaceDaoHandler"));
            definition.getPropertyValues().add("daoInterface", definition.getBeanClassName());
            definition.setBeanClass(RestClientFactoryBean.class);
            log.info("proxy:{}",definition.getBeanClassName());
        }

        return beanDefinitions;
    }

    /**
     * 默认不允许接口的,这里重写,覆盖下,另外默认会Scan @Component 这样所以的被@Component 注解的都会Scan
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
}
