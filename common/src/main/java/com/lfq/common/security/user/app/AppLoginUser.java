package com.lfq.common.security.user.app;

import com.lfq.common.security.user.BaseLoginUser;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * app用户登录用户身份权限
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
@Data
@NoArgsConstructor
public class AppLoginUser extends BaseLoginUser {

    private static final long serialVersionUID = 1L;

    private boolean isVip;


    public AppLoginUser(Long userId, Boolean isVip, String cachedKey) {
        this.cachedKey = cachedKey;
        this.userId = userId;
        this.isVip = isVip;
    }


}
