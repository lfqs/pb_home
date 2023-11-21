package com.lfq.common.security.user.web;

import com.lfq.common.security.user.BaseLoginUser;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
@Data
@NoArgsConstructor
public class SystemLoginUser extends BaseLoginUser {
    private static final long serialVersionUID = 1L;

    private boolean isAdmin;

    private Long deptId;

    private RoleInfo roleInfo;

    /**
     * 当超过这个时间 则触发刷新缓存时间
     */
    private Long autoRefreshCacheTime;


    public SystemLoginUser(Long userId, Boolean isAdmin, String username, String password, RoleInfo roleInfo,
                           Long deptId) {
        this.userId = userId;
        this.isAdmin = isAdmin;
        this.username = username;
        this.password = password;
        this.roleInfo = roleInfo;
        this.deptId = deptId;
    }

    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public Long getRoleId() {
        return getRoleInfo().getRoleId();
    }

    public Long getDeptId() {
        return deptId;
    }
}
