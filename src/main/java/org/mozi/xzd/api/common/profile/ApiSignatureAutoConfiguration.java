package org.mozi.xzd.api.common.profile;

import org.mozi.xzd.api.common.crypto.factory.AkskFactoryForStrategy;
import org.mozi.xzd.api.common.crypto.factory.AkskStrategy;
import org.mozi.xzd.api.common.crypto.factory.strategy.*;
import org.mozi.xzd.api.common.http.auth.DefaultSignAuthCheck;
import org.mozi.xzd.api.common.http.auth.DefaultTokenAuthCheck;
import org.mozi.xzd.api.common.http.auth.SignAuthCheck;
import org.mozi.xzd.api.common.http.auth.TokenAuthCheck;
import org.mozi.xzd.api.common.http.auth.aspect.CheckAuthAspect;
import org.mozi.xzd.api.common.profile.properties.ApiClientSignatureProperties;
import org.mozi.xzd.api.common.profile.properties.ApiServerSignatureProperties;
import org.mozi.xzd.api.common.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@ConditionalOnProperty(name = "spring.api.enabled",matchIfMissing = true)
@Configuration
@EnableConfigurationProperties({ ApiServerSignatureProperties.class, ApiClientSignatureProperties.class})
@Slf4j
public class ApiSignatureAutoConfiguration {
    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public CheckAuthAspect checkAuthAspect() {
        return new CheckAuthAspect();
    }


    @Bean
    public SignAuthCheck signAuthCheck() {
        return new DefaultSignAuthCheck();
    }

    @Bean
    public TokenAuthCheck tokenAuthCheck() {
        return new DefaultTokenAuthCheck();
    }


    @Bean
    public AkskFactoryForStrategy akskFactoryForStrategy() {
        return new AkskFactoryForStrategy();
    }

    @Bean("HMAC_SHA256")
    public AkskStrategy HMACSHA256Strategy() {
        return new HmacSha256Strategy();
    }

    @Bean("md2")
    public AkskStrategy md2Strategy() {
        return new Md2HexStrategy();
    }

    @Bean("md5Hex")
    public AkskStrategy md5HexStrategy() {
        return new Md5HexStrategy();
    }

    @Bean("md5")
    public AkskStrategy md5Strategy() {
        return new Md5Strategy();
    }

    @Bean("sha256Hex")
    public AkskStrategy sha256HexStrategy() {
        return new Sha256HexStrategy();
    }

    @Bean("sha256")
    public AkskStrategy sha256Strategy() {
        return new Sha256Strategy();
    }

}
