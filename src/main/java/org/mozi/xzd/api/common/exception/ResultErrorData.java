package org.mozi.xzd.api.common.exception;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/8/7 10:41
 */

@Data
@Builder
public class ResultErrorData {

    private int code;

    private String retMsg;

    private String message;

    private String path;

    private Timestamp dateTime;
}
