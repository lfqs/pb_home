package com.lfq.common.core.exception;

/**
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
public interface ErrorCodeInterface {

    /**
     * 返回错误码
     * @return 错误码
     */
    int code();

    /**
     * 返回具体的详细错误描述
     * @return 错误描述
     */
    String message();

    /**
     * i18n资源文件的key, 详见messages.properties文件
     * @return key
     */
    String i18nKey();

}
