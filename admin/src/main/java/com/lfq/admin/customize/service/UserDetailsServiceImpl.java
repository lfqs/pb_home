package com.lfq.admin.customize.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lfq.common.core.exception.ApiException;
import com.lfq.common.core.exception.ErrorCode;
import com.lfq.common.enums.BasicEnumUtil;
import com.lfq.common.enums.common.UserStatusEnum;
import com.lfq.common.security.user.web.DataScopeEnum;
import com.lfq.common.security.user.web.RoleInfo;
import com.lfq.common.security.user.web.SystemLoginUser;
import com.lfq.service.system.menu.entity.SysMenuEntity;
import com.lfq.service.system.menu.service.SysMenuService;
import com.lfq.service.system.role.entity.SysRoleEntity;
import com.lfq.service.system.role.service.SysRoleService;
import com.lfq.service.system.user.entity.SysUserEntity;
import com.lfq.service.system.user.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserService sysUserService;
    private final SysMenuService menuService;
    private final SysRoleService roleService;
    private final TokenService tokenService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserService.getUserByUserName(username);
        if (userEntity == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new ApiException(ErrorCode.Business.USER_NON_EXIST, username);
        }
        if (!Objects.equals(UserStatusEnum.NORMAL.getValue(), userEntity.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ApiException(ErrorCode.Business.USER_IS_DISABLE, username);
        }
        RoleInfo roleInfo = getRoleInfo(userEntity.getRoleId(), userEntity.getIsAdmin());

        SystemLoginUser loginUser = new SystemLoginUser(userEntity.getUserId(), userEntity.getIsAdmin(), userEntity.getUsername(),
                userEntity.getPassword(), roleInfo, userEntity.getDeptId());
        loginUser.fillLoginInfo();
        loginUser.setAutoRefreshCacheTime(loginUser.getLoginInfo().getLoginTime()
                + TimeUnit.MINUTES.toMillis(tokenService.getAutoRefreshTime()));
        return loginUser;
    }





    public RoleInfo getRoleInfo(Long roleId,boolean isAdmin){
        if (roleId == null) {
            return RoleInfo.EMPTY_ROLE;
        }
        if (isAdmin) {
            LambdaQueryWrapper<SysMenuEntity> menuQuery = Wrappers.lambdaQuery();
            menuQuery.select(SysMenuEntity::getMenuId);
            List<SysMenuEntity> allMenus = menuService.list(menuQuery);

            Set<Long> allMenuIds = allMenus.stream().map(SysMenuEntity::getMenuId).collect(Collectors.toSet());

            return new RoleInfo(RoleInfo.ADMIN_ROLE_ID, RoleInfo.ADMIN_ROLE_KEY, DataScopeEnum.ALL, SetUtils.emptySet(),
                    RoleInfo.ADMIN_PERMISSIONS, allMenuIds);

        }

        SysRoleEntity roleEntity = roleService.getById(roleId);

        if (roleEntity == null) {
            return RoleInfo.EMPTY_ROLE;
        }

        List<SysMenuEntity> menuList = roleService.getMenuListByRoleId(roleId);

        Set<Long> menuIds = menuList.stream().map(SysMenuEntity::getMenuId).collect(Collectors.toSet());
        Set<String> permissions = menuList.stream().map(SysMenuEntity::getPermission).collect(Collectors.toSet());

        DataScopeEnum dataScopeEnum = BasicEnumUtil.fromValue(DataScopeEnum.class, roleEntity.getDataScope());

        Set<Long> deptIdSet = SetUtils.emptySet();
        if (StrUtil.isNotEmpty(roleEntity.getDeptIdSet())) {
            deptIdSet = StrUtil.split(roleEntity.getDeptIdSet(), ",").stream()
                    .map(Convert::toLong).collect(Collectors.toSet());
        }

        return new RoleInfo(roleId, roleEntity.getRoleKey(), dataScopeEnum, deptIdSet, permissions, menuIds);
    }

}
