package org.mozi.xzd.api.common.http;

import lombok.Data;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/29 16:22
 */
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;
}