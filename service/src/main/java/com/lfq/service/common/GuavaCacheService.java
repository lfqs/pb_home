package com.lfq.service.common;

import com.lfq.common.cache.guava.AbstractGuavaCacheTemplate;
import com.lfq.service.system.config.service.SysConfigService;
import com.lfq.service.system.dept.entity.SysDeptEntity;
import com.lfq.service.system.dept.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @作者 lfq
 * @DATE 2023-11-23
 * current year
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class GuavaCacheService {


    private final SysConfigService configService;

    private final SysDeptService deptService;

    public final AbstractGuavaCacheTemplate<String> configCache = new AbstractGuavaCacheTemplate<String>() {
        @Override
        public String getObjectFromDb(Object id) {
            return configService.getConfigValueByKey(id.toString());
        }
    };

    public final AbstractGuavaCacheTemplate<SysDeptEntity> deptCache = new AbstractGuavaCacheTemplate<SysDeptEntity>() {
        @Override
        public SysDeptEntity getObjectFromDb(Object id) {
            return deptService.getById(id.toString());
        }
    };

}
