/*
 * Copyright (c) 2010-2030 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */

package com.tencent.nameservice.sdk;

import com.tencent.polaris.api.core.ConsumerAPI;

/**
 * ConsumerAPIHolder.
 *
 * @author tenkye
 * @date 2021/5/31 8:34 下午
 */
public class ConsumerAPIHolder {

    private static ConsumerAPI consumerAPI;

    public static void hold(ConsumerAPI consumer) {
        consumerAPI = consumer;
    }

    public static ConsumerAPI get() {
        return consumerAPI;
    }
}
