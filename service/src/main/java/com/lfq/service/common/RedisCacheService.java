package com.lfq.service.common;

import cn.hutool.extra.spring.SpringUtil;
import com.lfq.common.cache.redis.CacheKeyEnum;
import com.lfq.common.cache.redis.RedisCacheTemplate;
import com.lfq.common.security.user.web.SystemLoginUser;
import com.lfq.common.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
@Component
@RequiredArgsConstructor
public class RedisCacheService {

    private final RedisUtil redisUtil;

    public RedisCacheTemplate<String> captchaCache;
    public RedisCacheTemplate<SystemLoginUser> loginUserCache;
//    public RedisCacheTemplate<SysUserEntity> userCache;
//    public RedisCacheTemplate<SysRoleEntity> roleCache;
//    public RedisCacheTemplate<SysPostEntity> postCache;


    @PostConstruct
    public void init() {
        captchaCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.CAPTCHAT);
        loginUserCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.LOGIN_USER_KEY);
//        userCache = new RedisCacheTemplate<SysUserEntity>(redisUtil, CacheKeyEnum.USER_ENTITY_KEY) {
//            @Override
//            public SysUserEntity getObjectFromDb(Object id) {
//                SysUserService userService = SpringUtil.getBean(SysUserService.class);
//                return userService.getById((Serializable) id);
//            }
//        };
//        roleCache = new RedisCacheTemplate<SysRoleEntity>(redisUtil, CacheKeyEnum.ROLE_ENTITY_KEY) {
//            @Override
//            public SysRoleEntity getObjectFromDb(Object id) {
//                SysRoleService roleService = SpringUtil.getBean(SysRoleService.class);
//                return roleService.getById((Serializable) id);
//            }
//        };
//        postCache = new RedisCacheTemplate<SysPostEntity>(redisUtil, CacheKeyEnum.POST_ENTITY_KEY) {
//            @Override
//            public SysPostEntity getObjectFromDb(Object id) {
//                SysPostService postService = SpringUtil.getBean(SysPostService.class);
//                return postService.getById((Serializable) id);
//            }
//
//        };
    }
}