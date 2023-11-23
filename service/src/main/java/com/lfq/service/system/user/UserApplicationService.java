package com.lfq.service.system.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.lfq.service.system.user.service.SysUserService;
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


}
