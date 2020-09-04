package org.mozi.xzd.api.common.http.annotation;

import org.mozi.xzd.api.common.crypto.domain.vo.SignatureMethod;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.lang.annotation.*;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/11 10:18
 */

@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

    @AliasFor("path")
    String value() default "";

    @AliasFor("value")
    String path() default "";


    /**
     * 加密方案选择
     * <pre class="code">
     * signatureMethod = SignatureMethod.HMAC_SHA256
     * </pre>
     */
    SignatureMethod signatureMethod() default SignatureMethod.HMAC_SHA256;



    HttpMethod method() default HttpMethod.POST;



    String produces() default MediaType.APPLICATION_JSON_VALUE;

}
