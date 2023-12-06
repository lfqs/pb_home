package com.lfq.service.system.role.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfq.service.system.menu.entity.SysMenuEntity;
import com.lfq.service.system.role.entity.SysRoleEntity;
import com.lfq.service.system.role.mapper.SysRoleMapper;
import com.lfq.service.system.role.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author lfq
 * @since 2023-11-24
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Override
    public List<SysMenuEntity> getMenuListByRoleId(Long roleId) {
        return baseMapper.getMenuListByRoleId(roleId);
    }
}
