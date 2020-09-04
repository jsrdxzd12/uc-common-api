package org.mozi.xzd.api.common.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/30 10:47
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Data
public class SysAppSecret {

    private String id;

    private String name;

    private String accessKey;

    private String secretKey;

    private String introduction;

    private Integer deleted;

    private String createUser;

    private String createUserName;

    private String createDepart;

    private String createDepartName;

    private String updateUser;

    private String updateDepart;

    //@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createTime;

   // @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updateTime;

}
