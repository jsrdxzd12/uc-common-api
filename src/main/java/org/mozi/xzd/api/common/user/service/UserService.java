package org.mozi.xzd.api.common.user.service;

import org.mozi.xzd.api.common.exception.SignatureException;
import org.mozi.xzd.api.common.http.RestClient;
import org.mozi.xzd.api.common.http.domain.RestClientParam;
import org.mozi.xzd.api.common.profile.properties.ApiClientSignatureProperties;
import org.mozi.xzd.api.common.user.domain.SysAppSecret;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/4 07:59
 */
@Component
public class UserService {

    @Resource
    private RestClient restClient;

    @Resource
    private ApiClientSignatureProperties apiClientSignatureProperties;

    public SysAppSecret getAppSecretByAccessKey(String accessKey) throws SignatureException {
        Map<String, String> value = new HashMap<>();
        value.put("accessKey", accessKey);
        RestClientParam restClientParam = new RestClientParam().body(value).path("secret/secretByKey");

        Assert.isTrue(StringUtils.hasLength(apiClientSignatureProperties.getServerUrl()) && StringUtils.hasLength(apiClientSignatureProperties.getServerInstance()),"需要设置验证服务器地址！");

        if(StringUtils.hasLength(apiClientSignatureProperties.getServerInstance())){
            restClientParam.instances(apiClientSignatureProperties.getServerInstance());
        }
        if(StringUtils.hasLength(apiClientSignatureProperties.getServerUrl())){
            restClientParam.instances(apiClientSignatureProperties.getServerUrl());
        }

        ResponseEntity<SysAppSecret> objectResponseEntity = restClient.server(restClientParam,
                SysAppSecret.class);
        if (Objects.nonNull(objectResponseEntity) && objectResponseEntity.getStatusCodeValue() == 200) {
            SysAppSecret body = objectResponseEntity.getBody();
            return body;
        }
        throw new SignatureException("未能正确返回集");
    }
}