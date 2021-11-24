/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */

package com.tencent.nameservice.sdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * PolarisSdkAutoConfigurationTest.
 *
 * @author tenkye
 * @date 2021/11/20 1:29 下午
 */
@Slf4j
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PolarisConsumerAutoConfiguration.class)
@TestPropertySource("classpath:application.properties")
@PolarisConsumerScan("com.tencent.nameservice.sdk.demo")
class PolarisConsumerAutoConfigurationTest {

    @Test
    void testProperties() {

        log.info("test");

    }
}