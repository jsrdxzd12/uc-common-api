package org.mozi.xzd.api.common.http.auth.aspect;

import java.lang.annotation.*;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p>验证鉴权</p >
 * @since 2020/8/3 23:42
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthSignature {
}
