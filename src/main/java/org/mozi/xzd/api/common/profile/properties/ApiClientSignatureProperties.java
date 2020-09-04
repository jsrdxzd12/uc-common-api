package org.mozi.xzd.api.common.profile.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/28 22:49
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.api.client.signature")
public class ApiClientSignatureProperties {

    /**
     * accessKey
     */
    private String accessKey = "X-ACCESS_KEY";

    /**
     * 当前 UNIX 时间戳可记录发起 API 请求的时间。
     * 例如 1529223702。注意：如果与服务器时间相差超过5分钟，会引起签名过期错误。
     */
    private String timestamp = "X-Timestamp";

    /**
     * 请求签名，用来验证此次请求的合法性，需要用户根据实际的输入参数计算得出。具体计算方法参见接口鉴权部分说明。
     */
    private String signature = "X-SIGNATURE";
    /**
     * 操作的 API 的版本。取值参考接口文档中入参公共参数 Version 的说明。
     */
    private String version="X-Version";

    /**
     * 随机6位正整数，与 Timestamp 联合起来，用于防止重放攻击。
     */
    private String nonce="X-NONCE";

    /**
     * server code 编码
     */
    private String serverCode="X-SERVER-CODE";

    /**
     * 加密方案选择 默认HMAC_SHA256
     */
    private String signatureMethod="X-SIGNATURE-METHOD";

    /**
     * 验签服务地址 服务发现地址  示例http://uc-user-center
     */
    private String serverInstance="";

    /**
     * 验签服务地址 远程访问
     */
    private String serverUrl="";

    /**
     * BearerToken Authorization
     */
    private String bearerToken="Authorization";

    /**
     * 多AK SK 配置项 首个默认配置项
     */
    private String signatureSelectConfigDefaultKey="default";

    /**
     * 多AK SK 配置项
     */
    private Map<String, ApiSignatureConfig> signatureConfig;
}
