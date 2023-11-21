package com.lfq.common.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lfq.common.core.exception.ApiException;
import com.lfq.common.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 响应信息主体
 * @作者 lfq
 * @DATE 2023-11-21
 * current year
 **/
@Data
@AllArgsConstructor
public class ResponseVO<T> {

    private Integer code;

    private String msg;

    @JsonInclude
    private T data;

    public static <T> ResponseVO<T> ok() {
        return build(null, ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.message());
    }

    public static <T> ResponseVO<T> ok(T data) {
        return build(data, ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.message());
    }

    public static <T> ResponseVO<T> fail() {
        return build(null, ErrorCode.FAILED.code(), ErrorCode.FAILED.message());
    }

    public static <T> ResponseVO<T> fail(T data) {
        return build(data, ErrorCode.FAILED.code(), ErrorCode.FAILED.message());
    }

    public static <T> ResponseVO<T> fail(ApiException exception) {
        return build(null, exception.getErrorCode().code(), exception.getMessage());
    }

    public static <T> ResponseVO<T> fail(ApiException exception, T data) {
        return build(data, exception.getErrorCode().code(), exception.getMessage());
    }

    public static <T> ResponseVO<T> build(T data, Integer code, String msg) {
        return new ResponseVO<>(code, msg, data);
    }

}
