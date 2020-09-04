package org.mozi.xzd.api.common.http.invocation;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import org.mozi.xzd.api.common.http.RestClient;
import org.mozi.xzd.api.common.http.annotation.Service;
import org.mozi.xzd.api.common.http.domain.RestClientParam;
import org.mozi.xzd.api.common.json.JsonMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/10 14:22
 */
public class RestClientInvocationHandler implements InvocationHandler {

    @Resource
    private RestClient restClient;

    private Class<?> target;

    public void setTarget(Class<?> target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args)   {

        org.mozi.xzd.api.common.http.annotation.RestClient restClientAnnotation = target.getAnnotation(org.mozi.xzd.api.common.http.annotation.RestClient.class);

        Assert.notNull(restClientAnnotation, "程序中需要【com.jd.icity.api.common.http.annotation.RestClient】");

        Service service = method.getAnnotation(Service.class);

        String serverUrl = restClientAnnotation.url();
        String instances=StringUtils.hasLength(restClientAnnotation.instances())? restClientAnnotation.instances() :restClientAnnotation.value();

        String path = service.value();

        boolean isServerUrl=StringUtils.hasLength(serverUrl) || StringUtils.hasLength(instances)  ;

        Assert.notNull(path, "【path】缺少参数值");
        Assert.isTrue(isServerUrl, "【 serverUrl | instances 】至少需要一个参数");

        Map<String, Object> argMaps = new HashMap<>();

        Parameter[] parameters = method.getParameters();

        HttpHeaders httpHeaders = new HttpHeaders();
        for (int i = 0; i < parameters.length; i++) {
            Object param = args[i];

            Parameter parameter = parameters[i];
            if (param == null || parameter == null) {
                continue;
            }

            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
            RequestHeader requestHeader = parameter.getAnnotation(RequestHeader.class);

            if (Objects.nonNull(requestParam)) {

                if(param.getClass().isAssignableFrom(Object.class)){
                    argMaps.putAll(BeanUtil.beanToMap(param));
                }else {
                    Assert.isTrue(StringUtils.hasLength(requestParam.value()), "@RequestParam 需要设定参数名称");
                    argMaps.put(requestParam.value(), param);
                }
            }

            if (Objects.nonNull(requestBody)) {
                argMaps.putAll(BeanUtil.beanToMap(param));
            }


            if (Objects.nonNull(requestHeader)) {
                Assert.isTrue(StringUtils.hasLength(requestParam.value()), "@RequestHeader 需要设定参数名称");
                String json;
                if (param instanceof String) {
                    json = Convert.toStr(param);
                } else {
                    json = JsonMapper.objectToJsonPretty(param);
                }
                httpHeaders.add(requestHeader.value(), Convert.toStr(json));
            }
        }

        Map<String,Object> argValues=new HashMap<>();
        for (String k : argMaps.keySet()) {
            if (Objects.nonNull(argMaps.get(k))) {
                argValues.put(k,argMaps.get(k));
            }
        }


        RestClientParam restClientParam = new RestClientParam()
                .serverUrl(serverUrl )
                .instances(instances)
                .path(path)
                .accessKey(restClientAnnotation.accessKey())
                .secretKey(restClientAnnotation.secretKey())
                .httpMethod(service.method())
                .signatureMethod(service.signatureMethod())
                .mediaType(MediaType.valueOf(service.produces()))
                .headers(httpHeaders).body(argValues);
        return   restClient.server(restClientParam, getGenericReturnType(method));
    }

    protected Class<?> getGenericReturnType(Method method) {

        if (!"List".equals(method.getReturnType().getSimpleName())){
            return Object.class;
        }

        Type type = method.getGenericReturnType();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return (Class<?>) (parameterizedType.getActualTypeArguments()[0]);
    }
}