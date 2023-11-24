package com.lfq.service.system.user;

import com.lfq.common.security.user.web.SystemLoginUser;
import com.lfq.service.common.CacheCenter;
import com.lfq.service.system.user.dto.UserDTO;
import com.lfq.service.system.user.service.SysUserService;
import com.lfq.service.vo.CurrentLoginUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @作者 lfq
 * @DATE 2023-11-22
 * current year
 **/
@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final SysUserService userService;

    public boolean isUserNameDuplicated(String username){
        return userService.isUserNameDuplicated(username);
    }

    /**
     * 获取当前登录用户信息
     * @return 当前登录用户信息
     */
    public CurrentLoginUserDTO getLoginUserInfo(SystemLoginUser loginUser) {
        CurrentLoginUserDTO permissionDTO = new CurrentLoginUserDTO();

        permissionDTO.setUserInfo(new UserDTO(CacheCenter.userCache.getObjectById(loginUser.getUserId())));
        permissionDTO.setRoleKey(loginUser.getRoleInfo().getRoleKey());
        permissionDTO.setPermissions(loginUser.getRoleInfo().getMenuPermissions());
        return permissionDTO;
    }





}
