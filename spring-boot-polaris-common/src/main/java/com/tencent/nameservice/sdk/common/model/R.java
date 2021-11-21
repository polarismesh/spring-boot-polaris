/*
 * Copyright (c) 2010-2030 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */

package com.tencent.nameservice.sdk.common.model;

import com.tencent.nameservice.sdk.common.enums.ApiErrorCodeEnum;
import java.io.Serializable;
import java.util.Optional;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * REST API 返回结果
 *
 * @author hubin
 * @since 2018-06-05
 */
@ToString
@Data
@Accessors(chain = true)
public class R<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 业务错误码
     */
    private long code;
    /**
     * 结果集
     */
    private T data;
    /**
     * 描述
     */
    private String msg;

    public R() {
        // to do nothing
    }

    public R(ApiErrorCodeEnum errorCode) {
        errorCode = Optional.ofNullable(errorCode).orElse(ApiErrorCodeEnum.FAILED);
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public static <T> R<T> ok(T data) {
        ApiErrorCodeEnum aec = ApiErrorCodeEnum.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiErrorCodeEnum.FAILED;
        }
        return restResult(data, aec);
    }

    public boolean ok() {
        return ApiErrorCodeEnum.SUCCESS.getCode() == code;
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, ApiErrorCodeEnum.FAILED.getCode(), msg);
    }

    public static <T> R<T> failed(ApiErrorCodeEnum errorCode) {
        return restResult(null, errorCode);
    }

    public static <T> R<T> restResult(T data, ApiErrorCodeEnum errorCode) {
        return restResult(data, errorCode.getCode(), errorCode.getMsg());
    }

    private static <T> R<T> restResult(T data, long code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
