package org.mozi.xzd.api.common.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author xuzidong
 * @version V1.0.0

 * @description <p></p >
 * @since 2020/7/30 23:18
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class UserInfoBo {

    /**
     * 编号
     */
    private String id;
    /**
     * 帐号
     */
    private String account;

    /**
     * 用户
     */
    private String userName;

    /**
     * 权限
     */
    private String roleList;

    /**
     * 部门
     */
    private String departmentName;

    /**
     * 部门编号
     */
    private String departmentId;


    /**
     * 类型
     */
    private String type;

}
