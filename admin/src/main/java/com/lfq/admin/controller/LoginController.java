package com.lfq.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.lfq.admin.customize.domain.vo.ConfigVO;
import com.lfq.admin.customize.domain.vo.LoginVO;
import com.lfq.admin.customize.service.login.LoginService;
import com.lfq.common.core.exception.ApiException;
import com.lfq.common.core.response.ResponseVO;
import com.lfq.common.security.user.AuthenticationUtils;
import com.lfq.common.security.user.web.SystemLoginUser;
import com.lfq.service.system.menu.MenuApplicationService;
import com.lfq.service.system.menu.dto.RouterDTO;
import com.lfq.service.vo.CurrentLoginUserDTO;
import com.lfq.service.vo.TokenDTO;
import com.lfq.mybatisplus.config.PbHomeConfig;
import com.lfq.service.system.user.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    private final MenuApplicationService menuApplicationService;


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


    /**
     * 获取路由信息
     * TODO 如果要在前端开启路由缓存的话 需要在ServerConfig.json 中  设置CachingAsyncRoutes=true  避免一直重复请求路由接口
     * @return 路由信息
     */
    //@Operation(summary = "获取用户对应的菜单路由", description = "用于动态生成路由")
    @GetMapping("/getRouters")
    public ResponseVO<List<RouterDTO>> getRouters() {
        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
        List<RouterDTO> routerTree = menuApplicationService.getRouterTree(loginUser);
        return ResponseVO.ok(routerTree);
    }


//    @Operation(summary = "注册接口", description = "暂未实现")
//    @PostMapping("/register")
//    public ResponseVO<Void> register(@RequestBody AddUserCommand command) {
//        return ResponseVO.fail(new ApiException(Business.COMMON_UNSUPPORTED_OPERATION));
//    }

}
