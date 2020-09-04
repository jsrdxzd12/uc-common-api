package org.mozi.xzd.api.common.crypto.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author xuzidong
 * @version V1.0.0
 * @since 2020/7/29 20:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ApiSignVo {
    private String accessKey;

    private String secretKey;
}
