package com.lfq.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.lfq.mybatisplus.config.PbHomeConfig;
import com.lfq.service.system.user.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final PbHomeConfig pbHomeConfig;

    private final UserApplicationService userApplicationService;


    /**
     * 测试
     */
    @GetMapping("/getConfig")
    public boolean test(String username) {
        System.out.println("1111111111111111111111111111111111111111111");
        return userApplicationService.isUserNameDuplicated(username);
    }



    /**
     * 访问首页，提示语
     */
    @GetMapping("/")
    public String index() {
        return StrUtil.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", pbHomeConfig.getName(), pbHomeConfig.getVersion());
    }


}
