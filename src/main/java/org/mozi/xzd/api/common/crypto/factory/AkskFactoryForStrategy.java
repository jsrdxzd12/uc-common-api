package org.mozi.xzd.api.common.crypto.factory;

import org.mozi.xzd.api.common.exception.SignatureException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/5 22:15
 */
@Service
public class AkskFactoryForStrategy {


    @Resource
    Map<String, AkskStrategy> akskStrategyMaps = new ConcurrentHashMap<>(6);


    public AkskStrategy getAkskStrategy(String component) throws SignatureException {
        AkskStrategy akskStrategy=akskStrategyMaps.get(component);
        if(Objects.isNull(akskStrategy)){
            throw new SignatureException("不能被发现的算法实现类");
        }
        return akskStrategy;
    }
}
