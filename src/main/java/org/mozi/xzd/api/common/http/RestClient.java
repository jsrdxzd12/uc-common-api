package org.mozi.xzd.api.common.http;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.mozi.xzd.api.common.crypto.factory.AkskFactoryForStrategy;
import org.mozi.xzd.api.common.crypto.factory.AkskStrategy;
import org.mozi.xzd.api.common.exception.SignatureException;
import org.mozi.xzd.api.common.http.domain.RestClientParam;
import org.mozi.xzd.api.common.map.MapUtils;
import org.mozi.xzd.api.common.crypto.ApiSignUtils;
import org.mozi.xzd.api.common.profile.properties.ApiClientSignatureProperties;
import org.mozi.xzd.api.common.profile.properties.ApiSignatureConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/2 23:20
 */
@Slf4j
@Component
public class RestClient {

    @Resource
    private ApiClientSignatureProperties apiClientSignatureProperties;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private AkskFactoryForStrategy akskFactoryForStrategy;

    private static final RestTemplate REST_TEMPLATE ;
    private static final Cache<String, RestTemplate> CACHE;

    private Duration readTimeOut;

    private Duration connectTimeOut;

    private Boolean setTimeout = false;

    static {
        REST_TEMPLATE = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        REST_TEMPLATE.getMessageConverters().add(converter);
        CACHE = CacheBuilder.newBuilder()
                .maximumSize(50)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build();
    }

    public ResponseEntity<String> server(RestClientParam restClientParam) throws SignatureException {
        return build(restClientParam,String.class);
    }

    public  <T> ResponseEntity<T> server(RestClientParam restClientParam,Class<T> classType) throws SignatureException {
        return build(restClientParam,classType);
    }

    private <T> ResponseEntity<T> build(RestClientParam restClientParam, Class<T> classType) throws SignatureException {

        MediaType mediaType = restClientParam.getMediaType();
        HttpMethod httpMethod = restClientParam.getHttpMethod();
        accessSignature(restClientParam);

        StringBuilder url = new StringBuilder();

        if (StringUtils.hasLength(restClientParam.getInstances())) {
            url.append(restClientParam.getInstances());
        } else {
            restTemplate = getRestTemplate();
            url.append(restClientParam.getServerUrl());
        }
        url.append("/").append(restClientParam.getPath());

        if (HttpMethod.GET.equals(httpMethod) || MediaType.APPLICATION_FORM_URLENCODED.equals(mediaType)) {
            url = new StringBuilder(getFormParam(url.toString(), restClientParam.getBody()));
        }

        HttpEntity<Object> request = new HttpEntity<>(restClientParam.getBody(), restClientParam.getHeaders());
        return restTemplate.exchange(url.toString(), httpMethod,
                request, classType);
    }

    private void loadSignatureProperties(ApiClientSignatureProperties signatureProperties){
        if(Objects.nonNull(signatureProperties)){
            BeanUtils.copyProperties(signatureProperties,apiClientSignatureProperties);
        }
    }

    private void accessSignature(  RestClientParam restClientParam) throws SignatureException {

        Assert.notNull(this.apiClientSignatureProperties, "apiSignatureProperties 未能被实例化");

        Map<String, ApiSignatureConfig> signatureConfigMap = this.apiClientSignatureProperties.getSignatureConfig();

        if (!StringUtils.hasLength(restClientParam.getAccessKey()) && !ObjectUtils.isEmpty(signatureConfigMap)) {
            String signatureConfigKey = restClientParam.getSignatureConfigKey(this.apiClientSignatureProperties.getSignatureSelectConfigDefaultKey());
            ApiSignatureConfig apiSignatureConfig = signatureConfigMap.get(signatureConfigKey);
            if (!Objects.isNull(apiSignatureConfig)) {
                restClientParam.accessKey(apiSignatureConfig.getAccessKey()).secretKey(apiSignatureConfig.getSecretKey());
            }
        }


        if (Objects.nonNull(restClientParam.getAccessKey())) {
            loadSignatureProperties(restClientParam.getSignatureProperties());

            String time = String.valueOf(System.currentTimeMillis());
            String randomNum = getRandomNum();
            String value = restClientParam.getSignatureMethod().getValue();
            Map<String, Object> paramMap = paramMap = new HashMap<>();
            Object v = restClientParam.getBody();
            if (v instanceof Map) {
                paramMap.putAll((Map<String, Object>) v);
            } else {
                paramMap.putAll(BeanUtil.beanToMap(restClientParam.getBody()));
            }
            paramMap.put(this.apiClientSignatureProperties.getAccessKey(), restClientParam.getAccessKey());
            paramMap.put(this.apiClientSignatureProperties.getTimestamp(), time);

            restClientParam.getHeaders().add(this.apiClientSignatureProperties.getSignatureMethod(), value);
            restClientParam.getHeaders().add(this.apiClientSignatureProperties.getNonce(), randomNum);
            restClientParam.getHeaders().add(this.apiClientSignatureProperties.getAccessKey(), restClientParam.getAccessKey());
            restClientParam.getHeaders().add(this.apiClientSignatureProperties.getTimestamp(), time);

            AkskStrategy akskStrategy = akskFactoryForStrategy.getAkskStrategy(value);
            restClientParam.getHeaders().add(this.apiClientSignatureProperties.getSignature(), ApiSignUtils.generateSignature(akskStrategy, MapUtils.toParamMap(paramMap),
                    restClientParam.getSecretKey()));
        }
    }

    private String getRandomNum() {
        return Convert.toStr((int)((Math.random()*9+1)*100000));
    }

    private String getFormParam(String path,Object entity) {

        Map<String, Object> param =new HashMap<>();

        if(Objects.nonNull(entity) &&  entity instanceof Map){
            param.putAll((Map<? extends String, ?>) entity);
        }else{
            param.putAll(BeanUtil.beanToMap(entity));
        }

        if (!ObjectUtils.isEmpty(param) && !param.isEmpty()) {
            StringBuilder pathBuilder = new StringBuilder(path+"?");
            param.forEach((k, v) -> pathBuilder.append(k).append("=").append(v).append("&"));
            int length = pathBuilder.length();
            pathBuilder.delete(length - 1, length);
            path = pathBuilder.toString();
        }
        return path;
    }
    /**
     * 获取restTemplate
     *
     * @return RestTemplate
     */
    public RestTemplate getRestTemplate() {
        if (!setTimeout) {
            return REST_TEMPLATE;
        }
        // 先去查看是否已经缓存了相同设置超时时间的restTemplate
        // 拼接规则为 ${readTimeout.toString()}:${connectTimeout.toString()}
        String cacheKey = generateCacheKey();
        RestTemplate timoutRestTemplate = CACHE.getIfPresent(cacheKey);
        if (Objects.nonNull(timoutRestTemplate)) {
            // 重置超时时间
            CACHE.put(cacheKey, timoutRestTemplate);
            return timoutRestTemplate;
        }
        // 之前没有缓存该restTemplate，生成好restTemplate，然后缓存起来
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        if (Objects.nonNull(this.readTimeOut)) {
            restTemplateBuilder.setReadTimeout(readTimeOut);
        }
        if (Objects.nonNull(this.connectTimeOut)) {
            restTemplateBuilder.setConnectTimeout(connectTimeOut);
        }
        RestTemplate restTemplate = restTemplateBuilder.build();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        restTemplate.getMessageConverters().add(converter);
        CACHE.put(cacheKey, restTemplate);
        return restTemplate;
    }

    private String generateCacheKey() {
        StringBuilder sb = new StringBuilder();
        if (Objects.nonNull(readTimeOut)) {
            sb.append(readTimeOut.toString());
        }
        if (Objects.nonNull(connectTimeOut)) {
            sb.append(connectTimeOut.toString());
        }
        return sb.toString();
    }
}
