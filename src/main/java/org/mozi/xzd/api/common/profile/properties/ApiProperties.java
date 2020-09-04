package org.mozi.xzd.api.common.profile.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/28 22:49
 */
@Data
@ConfigurationProperties(prefix = "spring.api")
public class ApiProperties {

    /**
     * 开启api接口
     * <pre>
     *     enabled: false
     * </pre>
     */
    private Boolean enabled=false;

    /**
     * componentRestClientPackages 配置地址
     *
     * 多个可以用逗号隔开
     * <pre>
     *      componentRestClientPackages: com.jd.icity.** ,com.jd.icity.**.interface
     * </pre>
     */
    private String componentRestClientPackages;

}
