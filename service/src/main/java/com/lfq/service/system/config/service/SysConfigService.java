package com.lfq.service.system.config.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lfq.service.system.config.entity.SysConfigEntity;

/**
 * <p>
 * 参数配置表 服务类
 * </p>
 *
 * @author lfq
 * @since 2023-11-23
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    /**
     * 通过key获取配置
     * @param key 配置对应的key
     * @return 配置
     */
    String getConfigValueByKey(String key);

}
