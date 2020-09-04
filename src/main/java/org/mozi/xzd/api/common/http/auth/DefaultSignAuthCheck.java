package org.mozi.xzd.api.common.http.auth;

import org.mozi.xzd.api.common.crypto.ApiSignUtils;
import org.mozi.xzd.api.common.crypto.factory.AkskFactoryForStrategy;
import org.mozi.xzd.api.common.crypto.factory.AkskStrategy;
import org.mozi.xzd.api.common.exception.SignatureException;
import org.mozi.xzd.api.common.json.RequestJsonUtils;
import org.mozi.xzd.api.common.profile.properties.ApiServerSignatureProperties;
import org.mozi.xzd.api.common.user.domain.SysAppSecret;
import org.mozi.xzd.api.common.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/5 14:22
 */
@Slf4j
//@Component
public class DefaultSignAuthCheck implements SignAuthCheck {


    @Resource
    private ApiServerSignatureProperties apiServerSignatureProperties;

    @Resource
    private UserService userService;

    @Resource
    private AkskFactoryForStrategy akskFactoryForStrategy;


    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void validSign(HttpServletRequest request,Object[] param) throws SignatureException, IOException {

        String accessKey = request.getHeader(apiServerSignatureProperties.getAccessKey());
        if (StringUtils.isEmpty(accessKey )) {
            throw new SignatureException("AccessKey 不能为空");
        }

        String timestamp = request.getHeader(apiServerSignatureProperties.getTimestamp());
        if (StringUtils.isEmpty(timestamp )) {
            throw new SignatureException("timestamp 不能为空");
        }

        Long durationMt = apiServerSignatureProperties.getDurationMt();
        long now = System.currentTimeMillis();
        long duration = Math.abs(now - Long.parseLong(timestamp));
        if (duration > durationMt) {
            throw new SignatureException("非法请求，请求时间戳已超时");
        }

        String nonce= request.getHeader(apiServerSignatureProperties.getNonce());
        if (StringUtils.isEmpty(nonce )) {
            throw new SignatureException("nonce 不能为空");
        }

        if(Objects.nonNull(redisTemplate.opsForValue().get(nonce))){
            throw new SignatureException("一次类似");
        }
        redisTemplate.opsForValue().set(nonce,"1",durationMt);

        String signature= request.getHeader(apiServerSignatureProperties.getSignature());
        if (StringUtils.isEmpty(signature )) {
            throw new SignatureException("signature 不能为空");
        }


        String signatureMethod= request.getHeader(apiServerSignatureProperties.getSignatureMethod());

        if (StringUtils.isEmpty(signatureMethod )) {
            throw new SignatureException("signatureMethod 不能为空");
        }



        Map<String, Object> paramMap = RequestJsonUtils.getRequestAopJsonObject(request,param);
        log.info("request.getQueryString():"+paramMap);

        paramMap.put(this.apiServerSignatureProperties.getAccessKey(), accessKey);
        paramMap.put(this.apiServerSignatureProperties.getTimestamp(), timestamp);
      //  paramMap.put(this.apiSignatureProperties.getNonce(), nonce);

        AkskStrategy akskStrategy= akskFactoryForStrategy.getAkskStrategy(signatureMethod);


        SysAppSecret sysAppSecret= userService.getAppSecretByAccessKey(accessKey);

        if(Objects.isNull(sysAppSecret)) {
            throw new SignatureException("不存在的验签");
        }
        String originSign = ApiSignUtils.generateSignature(akskStrategy, paramMap, sysAppSecret.getSecretKey());
        akskStrategy.matches(signature,originSign);
    }

}
