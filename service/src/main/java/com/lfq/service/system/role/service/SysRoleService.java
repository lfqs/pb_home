package com.lfq.service.system.role.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lfq.service.system.menu.entity.SysMenuEntity;
import com.lfq.service.system.role.entity.SysRoleEntity;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author lfq
 * @since 2023-11-24
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 获取用户的权限列表
     * @param roleId 角色id
     * @return 菜单列表
     */
    List<SysMenuEntity> getMenuListByRoleId(Long roleId);

}
