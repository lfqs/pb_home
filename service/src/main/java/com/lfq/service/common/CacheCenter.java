package com.lfq.service.common;

import cn.hutool.extra.spring.SpringUtil;
import com.lfq.common.cache.guava.AbstractGuavaCacheTemplate;
import com.lfq.common.cache.redis.RedisCacheTemplate;
import com.lfq.common.security.user.web.SystemLoginUser;
import com.lfq.service.system.dept.entity.SysDeptEntity;
import com.lfq.service.system.post.entity.SysPostEntity;
import com.lfq.service.system.role.entity.SysRoleEntity;
import com.lfq.service.system.user.entity.SysUserEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 缓存中心  提供全局访问点
 * 如果是领域类的缓存  可以自己新建一个直接放在CacheCenter   不用放在GuavaCacheService
 * 或者RedisCacheService
 * @作者 lfq
 * @DATE 2023-11-24
 * current year
 **/
@Component
public class CacheCenter {

    public static AbstractGuavaCacheTemplate<String> configCache;

    public static AbstractGuavaCacheTemplate<SysDeptEntity> deptCache;

    public static RedisCacheTemplate<String> captchaCache;

    public static RedisCacheTemplate<SystemLoginUser> loginUserCache;

    public static RedisCacheTemplate<SysUserEntity> userCache;

    public static RedisCacheTemplate<SysRoleEntity> roleCache;

    public static RedisCacheTemplate<SysPostEntity> postCache;

    @PostConstruct
    public void init() {
        GuavaCacheService guavaCache = SpringUtil.getBean(GuavaCacheService.class);
        RedisCacheService redisCache = SpringUtil.getBean(RedisCacheService.class);

        configCache = guavaCache.configCache;
        deptCache = guavaCache.deptCache;

        captchaCache = redisCache.captchaCache;
        loginUserCache = redisCache.loginUserCache;
        userCache = redisCache.userCache;
        roleCache = redisCache.roleCache;
        postCache = redisCache.postCache;
    }

}

