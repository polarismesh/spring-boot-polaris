/*
 * Copyright (c) 2010-2030 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */

package com.tencent.nameservice.sdk.common.enums;

import lombok.Getter;

/**
 * ApiErrorCodeEnum.
 *
 * @author tenkye
 * @date 2021/11/19 10:04 下午
 */
@Getter
public enum ApiErrorCodeEnum {
    /**
     * 失败
     */
    FAILED(-1, "操作失败"),
    /**
     * 成功
     */
    SUCCESS(0, "执行成功");

    private final long code;
    private final String msg;

    ApiErrorCodeEnum(final long code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiErrorCodeEnum fromCode(long code) {
        ApiErrorCodeEnum[] ecs = ApiErrorCodeEnum.values();
        for (ApiErrorCodeEnum ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    @Override
    public String toString() {
        return String.format(" ErrorCode:{code=%s, msg=%s} ", code, msg);
    }
}