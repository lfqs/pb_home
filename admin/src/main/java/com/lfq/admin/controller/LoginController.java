package com.lfq.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.lfq.admin.customize.domain.vo.ConfigVO;
import com.lfq.admin.customize.domain.vo.LoginVO;
import com.lfq.admin.customize.service.login.LoginService;
import com.lfq.common.core.response.ResponseVO;
import com.lfq.common.security.user.AuthenticationUtils;
import com.lfq.common.security.user.web.SystemLoginUser;
import com.lfq.service.vo.CurrentLoginUserDTO;
import com.lfq.service.vo.TokenDTO;
import com.lfq.mybatisplus.config.PbHomeConfig;
import com.lfq.service.system.user.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    private final LoginService loginService;




    /**
     * 访问首页，提示语
     */
    @GetMapping("/")
    public String index() {
        return StrUtil.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", pbHomeConfig.getName(), pbHomeConfig.getVersion());
    }



    /**
     * 获取系统配置信息sys_config data
     */
    @GetMapping("/getConfig")
    public ResponseVO<ConfigVO> getConfig() {
        ConfigVO configVO = loginService.getConfig();
        return ResponseVO.ok(configVO);
    }

    /**
     * 登录方法
     * @param loginCommand 登录信息
     * @return 结果
     */
//    @Operation(summary = "登录")
    @PostMapping("/login")
    public ResponseVO login(@RequestBody LoginVO loginCommand){
        // 生成令牌
        String token = loginService.login(loginCommand);
        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
        CurrentLoginUserDTO currentUserDTO = userApplicationService.getLoginUserInfo(loginUser);

        return ResponseVO.ok(new TokenDTO(token, currentUserDTO));
    }











}
