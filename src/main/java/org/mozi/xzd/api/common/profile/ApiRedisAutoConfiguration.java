package org.mozi.xzd.api.common.profile;

import org.mozi.xzd.api.common.json.JsonMapper;
import org.mozi.xzd.api.common.profile.properties.ApiCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.util.DigestUtils;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/12 00:30
 */

@Configuration
@EnableCaching
@AutoConfigureAfter({org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class})
@ConditionalOnProperty(name = "spring.api.cache.enabled",matchIfMissing = true)
@EnableConfigurationProperties({ApiCacheProperties.class})
@EnableRedisRepositories
@Slf4j
public class ApiRedisAutoConfiguration extends CachingConfigurerSupport {

    @Bean
    public GenericJackson2JsonRedisSerializer jsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setValueSerializer(jsonRedisSerializer());
        return template;
    }


    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder(o.getClass().getSimpleName()).append(".");
            sb.append(method.getName()).append("(");
            StringBuilder strItem = new StringBuilder();
            for (Object obj : objects) {
                if (obj == null) {
                    continue;
                }
                boolean isSimple = BeanUtils.isSimpleProperty(obj.getClass());
                if (isSimple) {
                    strItem.append(obj.getClass()).append("=").append(obj);
                } else {
                    strItem.append(JsonMapper.objectToJson(obj));
                }

            }
            sb.append(DigestUtils.md5DigestAsHex(strItem.toString().getBytes()));
            sb.append(")");
            log.info("缓存key:[{}]", sb.toString());
            return sb.toString();
        };
    }

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory)).build();
    }

}
