package com.lfq.common.enums;

/**
 * 枚举接口
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
public interface BasicEnum<T>{


    /**
     * 获取枚举的值
     * @return 枚举值
     */
    T getValue();

    /**
     * 获取枚举的描述
     * @return 描述
     */
    String description();


}
