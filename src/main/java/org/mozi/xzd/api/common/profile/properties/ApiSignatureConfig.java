package org.mozi.xzd.api.common.profile.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/28 22:49
 */
@Data
@Component
public class ApiSignatureConfig {

    /**
     * accessKey
     */
    private String accessKey = "X-ACCESS_KEY";

    /**
     * accessKey
     */
    private String secretKey = "X-SECRET_KEY";
}