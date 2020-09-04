package org.mozi.xzd.api.common.crypto.domain.vo;

import lombok.Getter;

/**
 * @author xuzidong
 * @version V1.0.0
 * @since 2020/8/6 09:42
 */
@Getter
public enum SignatureMethod {

    HMAC_SHA256("HMAC_SHA256"),
    MD2HEX("MD2HEX"),
    MD5HEX("MD5HEX"),
    MD5("MD5"),
    SHA256HEX("SHA256HEX"),
    SHA256("SHA256");

    String value;

    SignatureMethod(String value) {
        this.value = value;
    }
}
