package com.lfq.service.system.menu.model;

import com.lfq.common.core.exception.ApiException;
import com.lfq.common.core.exception.ErrorCode;
import com.lfq.service.system.menu.entity.SysMenuEntity;
import com.lfq.service.system.menu.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 菜单工厂
 * @作者 lfq
 * @DATE 2023-12-07
 * current year
 **/
@Component
@RequiredArgsConstructor
public class MenuModelFactory {

    private final SysMenuService menuService;

    public MenuModel loadById(Long menuId) {
        SysMenuEntity byId = menuService.getById(menuId);
        if (byId == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, menuId, "菜单");
        }
        return new MenuModel(byId, menuService);
    }

    public MenuModel create() {
        return new MenuModel(menuService);
    }

}
