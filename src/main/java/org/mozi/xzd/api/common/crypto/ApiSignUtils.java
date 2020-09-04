package org.mozi.xzd.api.common.crypto;


import org.mozi.xzd.api.common.crypto.domain.vo.ApiSignVo;
import org.mozi.xzd.api.common.crypto.factory.AkskStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p>AK/SK 加签 验签</p >
 * @since 2020/7/29 14:54
 */
@Slf4j
public class ApiSignUtils {

    private final static String PREFIX="JL";

    /**
     * 生成签名
     *
     * @param accessKey sk密钥
     * @param queryParams 传入的参数值
     * @return
     */
    public static String generateSignature(AkskStrategy akskStrategy, Map<String, Object> queryParams, String... accessKey) {
        String origin = origin(queryParams);
        return akskStrategy.encode(origin ,accessKey[0]);
    }

    /**
     * 生成AK/SK
     * @return 返回AK/SK对象
     */
    public static ApiSignVo generateAccessKey(){
        return new ApiSignVo()
                // 生成accessKey
                .setAccessKey(PREFIX+DigestUtils.md5Hex(UUID.randomUUID().toString()))
                // 生成SecretKey
                .setSecretKey(DigestUtils.sha1Hex(UUID.randomUUID().toString()));
    }


    /**
     * 获取原始排序值
     *
     * @param queryParams 传入的参数值
     * @return
     */
    public static String origin(Map<String, Object> queryParams) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> keySet = queryParams.keySet();
        List<String> keys = new ArrayList<>(keySet);
        Collections.sort(keys);
        for (String key : keys) {
            stringBuilder.append(key).append(queryParams.get(key));
        }
        return stringBuilder.toString();
    }

}
