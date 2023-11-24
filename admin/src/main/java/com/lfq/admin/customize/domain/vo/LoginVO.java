package com.lfq.admin.customize.domain.vo;

import lombok.Data;

/**
 * @作者 lfq
 * @DATE 2023-11-23
 * current year
 **/
@Data
public class LoginVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captchaCode;

    /**
     * 唯一标识
     */
    private String captchaCodeKey;

}
