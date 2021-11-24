/*
 * Copyright (c) 2010-2030 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */

package com.tencent.nameservice.sdk.consumer.runner;

import com.tencent.nameservice.sdk.consumer.remote.BookClient;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DemoRunner.
 *
 * @author tenkye
 * @date 2021/11/19 9:44 下午
 */
@Component
public class DemoRunner implements CommandLineRunner {

    @Resource
    private BookClient bookClient;

    @Override
    public void run(String... args) throws Exception {

        bookClient.getBook("aaaa");
    }
}
