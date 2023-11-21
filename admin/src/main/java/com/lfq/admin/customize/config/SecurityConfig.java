package com.lfq.admin.customize.config;

import cn.hutool.json.JSONUtil;
import com.lfq.common.core.exception.ApiException;
import com.lfq.common.core.exception.ErrorCode;
import com.lfq.common.core.response.ResponseVO;
import com.lfq.common.utils.ServletHolderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * 登录异常处理类
     * 用户未登陆的话  在这个Bean中处理
     */
    @Bean
    public AuthenticationEntryPoint unauthorizedHandler() {
        return (request, response, exception) -> {
            ResponseVO<Object> responseDTO = ResponseVO.fail(
                    new ApiException(ErrorCode.Client.COMMON_NO_AUTHORIZATION, request.getRequestURI())
            );
            ServletHolderUtil.renderString(response, JSONUtil.toJsonStr(responseDTO));
        };
    }

}
