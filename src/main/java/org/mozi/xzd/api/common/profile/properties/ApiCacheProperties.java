package org.mozi.xzd.api.common.profile.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/12 09:51
 */
@Data
@ConfigurationProperties(prefix = "spring.api.cache")
public class ApiCacheProperties {

    /**
     * 开启api缓存接口
     * <pre>
     *     enabled: false
     * </pre>
     */
    private Boolean enabled=false;
}
