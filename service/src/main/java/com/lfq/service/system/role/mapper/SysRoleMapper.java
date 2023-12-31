package com.lfq.service.system.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lfq.service.system.menu.entity.SysMenuEntity;
import com.lfq.service.system.role.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author lfq
 * @since 2023-11-24
 */
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    /**
     * 根据角色ID查询对应的菜单权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Select("SELECT m.* "
            + "FROM sys_menu m "
            + " LEFT JOIN sys_role_menu rm ON m.menu_id = rm.menu_id "
            + " LEFT JOIN sys_role r ON r.role_id = rm.role_id "
            + "WHERE m.status = 1 AND m.deleted = 0 "
            + " AND r.status = 1 AND r.deleted = 0 "
            + " AND r.role_id = #{roleId}")
    List<SysMenuEntity> getMenuListByRoleId(Long roleId);
}
