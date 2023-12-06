package com.lfq.admin.customize.service.login;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.extra.servlet.ServletUtil;
import com.lfq.admin.customize.async.AsyncTaskFactory;
import com.lfq.admin.customize.domain.vo.ConfigVO;
import com.lfq.admin.customize.domain.vo.LoginVO;
import com.lfq.admin.customize.service.TokenService;
import com.lfq.common.core.exception.ApiException;
import com.lfq.common.core.exception.ErrorCode;
import com.lfq.common.enums.common.ConfigKeyEnum;
import com.lfq.common.enums.common.LoginStatusEnum;
import com.lfq.common.security.user.web.SystemLoginUser;
import com.lfq.common.thread.ThreadPoolManager;
import com.lfq.common.utils.MessageUtils;
import com.lfq.common.utils.ServletHolderUtil;
import com.lfq.mybatisplus.config.PbHomeConfig;
import com.lfq.service.common.GuavaCacheService;
import com.lfq.service.common.MapCache;
import com.lfq.service.common.RedisCacheService;
import com.lfq.service.system.user.entity.SysUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @作者 lfq
 * @DATE 2023-11-22
 * current year
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginService {

    private final GuavaCacheService guavaCache;
    private final RedisCacheService redisCache;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public String login(LoginVO loginCommand){
        if(isCaptchaOn()){
            validateCaptcha(loginCommand.getUsername(), loginCommand.getCaptchaCode(), loginCommand.getCaptchaCodeKey());
        }
        // 用户验证
        Authentication authentication;
        //密码解密
        String decryptPassword = decryptPassword(loginCommand.getPassword());
        try{
            // 该方法会去调用UserDetailsServiceImpl#loadUserByUsername  校验用户名和密码  认证鉴权
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCommand.getUsername(), decryptPassword));
        }catch (BadCredentialsException e){
            ThreadPoolManager.execute(AsyncTaskFactory.loginInfoTask(loginCommand.getUsername(), LoginStatusEnum.LOGIN_FAIL, MessageUtils.message("Business.LOGIN_WRONG_USER_PASSWORD")));
            throw new ApiException(e, ErrorCode.Business.LOGIN_WRONG_USER_PASSWORD);
        } catch (AuthenticationException e) {
            ThreadPoolManager.execute(AsyncTaskFactory.loginInfoTask(loginCommand.getUsername(), LoginStatusEnum.LOGIN_FAIL, e.getMessage()));
            throw new ApiException(e, ErrorCode.Business.LOGIN_ERROR, e.getMessage());
        } catch (Exception e) {
            ThreadPoolManager.execute(AsyncTaskFactory.loginInfoTask(loginCommand.getUsername(), LoginStatusEnum.LOGIN_FAIL, e.getMessage()));
            throw new ApiException(e, ErrorCode.Business.LOGIN_ERROR, e.getMessage());
        }
        // 把当前登录用户 放入上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 这里获取的loginUser是UserDetailsServiceImpl#loadUserByUsername方法返回的LoginUser
        SystemLoginUser loginUser = (SystemLoginUser) authentication.getPrincipal();
        //记录登录信息
        recordLoginInfo(loginUser);
        // 生成token
        return tokenService.createTokenAndPutUserInCache(loginUser);
    }

    /**
     * 获取验证码 data
     */
    public ConfigVO getConfig() {
        ConfigVO configDTO = new ConfigVO();
        boolean isCaptchaOn = isCaptchaOn();
        configDTO.setIsCaptchaOn(isCaptchaOn);
        configDTO.setDictionary(MapCache.dictionaryCache());
        return configDTO;
    }

    /**
     * 校验验证码
     * @param username 用户名
     * @param captchaCode 验证码
     * @param captchaCodeKey 验证码对应的缓存key
     */
    public void validateCaptcha(String username, String captchaCode, String captchaCodeKey) {
        String captcha = redisCache.captchaCache.getObjectById(captchaCodeKey);
        redisCache.captchaCache.delete(captchaCodeKey);
        if (captcha == null) {
            ThreadPoolManager.execute(AsyncTaskFactory.loginInfoTask(username, LoginStatusEnum.LOGIN_FAIL,
                    ErrorCode.Business.LOGIN_CAPTCHA_CODE_EXPIRE.message()));
            throw new ApiException(ErrorCode.Business.LOGIN_CAPTCHA_CODE_EXPIRE);
        }
        if (!captchaCode.equalsIgnoreCase(captcha)) {
            ThreadPoolManager.execute(AsyncTaskFactory.loginInfoTask(username, LoginStatusEnum.LOGIN_FAIL, ErrorCode.Business.LOGIN_CAPTCHA_CODE_WRONG.message()));
            throw new ApiException(ErrorCode.Business.LOGIN_CAPTCHA_CODE_WRONG);
        }
    }

    /**
     * 记录登录信息
     * @param loginUser 登录用户
     */
    public void recordLoginInfo(SystemLoginUser loginUser) {
        ThreadPoolManager.execute(AsyncTaskFactory.loginInfoTask(loginUser.getUsername(), LoginStatusEnum.LOGIN_SUCCESS,
                LoginStatusEnum.LOGIN_SUCCESS.description()));

        SysUserEntity entity = redisCache.userCache.getObjectById(loginUser.getUserId());

        entity.setLoginIp(ServletUtil.getClientIP(ServletHolderUtil.getRequest()));
        entity.setLoginDate(DateUtil.date());
        entity.updateById();
    }

    /**
     * 密码解密
     * @param originalPassword
     * @return
     */
    public String decryptPassword(String originalPassword) {
        byte[] decryptBytes = SecureUtil.rsa(PbHomeConfig.getRsaPrivateKey(), null).decrypt(Base64.decode(originalPassword), KeyType.PrivateKey);
        return StrUtil.str(decryptBytes, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 是否打开验证码
     * @return
     */

    private boolean isCaptchaOn() {
        return Convert.toBool(guavaCache.configCache.get(ConfigKeyEnum.CAPTCHA.getValue()));
    }
}
