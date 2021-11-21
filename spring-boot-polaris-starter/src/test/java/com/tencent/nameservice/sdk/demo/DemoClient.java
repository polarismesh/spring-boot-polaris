/*
 * Copyright (c) 2010-2030 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */

package com.tencent.nameservice.sdk.demo;

import com.tencent.nameservice.sdk.PolarisConsumer;

/**
 * DemoClient.
 *
 * @author tenkye
 * @date 2021/11/20 1:39 下午
 */
@PolarisConsumer(id = "book")
public interface DemoClient {

    /**
     * Echo something.
     *
     * @param msg message
     * @return message
     */
    String echo(String msg);
}
