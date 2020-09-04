package org.mozi.xzd.api.common.http.annotation;

import org.mozi.xzd.api.common.crypto.domain.vo.SignatureMethod;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/10 11:12
 */

@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RestClient {


    /**
     *
     *  url
     */
    @AliasFor("instances")
    String value() default "";


    String url() default "";

    /**
     *
     *  url
     *  支持负载均衡、超时重试、熔断等
     */
    @AliasFor("value")
    String instances() default "";

    /**
     *
     * accessKey 应用编码
     *
     */
    String accessKey() default "";


    /**
     *  secretKey 服务编码
     *
     */
    String secretKey() default "";


    /**
     *
     *  请求API 版本号
     */
    String version() default "";


    /**
     * 加密方案选择
     * <pre class="code">
     * signatureMethod = SignatureMethod.HMAC_SHA256
     * </pre>
     */
    SignatureMethod signatureMethod() default SignatureMethod.HMAC_SHA256;

    /**
     *  多个AKSK列表选择
     *
     */
    String signatureSelectConfigKey() default "default";

}