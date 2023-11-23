package com.lfq.admin.customize.service.login;

import cn.hutool.core.convert.Convert;
import com.lfq.admin.customize.domain.vo.ConfigVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private boolean isCaptchaOn() {
        return Convert.toBool(guavaCache.configCache.get(ConfigKeyEnum.CAPTCHA.getValue()));
    }
}
