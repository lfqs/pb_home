package com.lfq.common.enums;

/**
 * 字典类型 接口
 * @作者 lfq
 * @DATE 2023-11-23
 * current year
 **/
public interface DictionaryEnum<T> extends BasicEnum<T> {

    /**
     * 获取css标签
     * @return css标签
     */
    String cssTag();

}
