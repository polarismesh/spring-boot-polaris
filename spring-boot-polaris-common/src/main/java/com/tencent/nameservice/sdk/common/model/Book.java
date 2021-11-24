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

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Book.
 *
 * @author tenkye
 * @date 2021/11/19 9:37 下午
 */
@Data
@Accessors(chain = true)
public class Book {

    private String id;
    private String name;
}
