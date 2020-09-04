package org.mozi.xzd.api.common.http.auth.aspect;

import java.lang.annotation.*;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/4 00:50
 */

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthToken {


    String[] value() default {};
}
