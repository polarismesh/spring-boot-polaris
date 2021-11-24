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
import com.tencent.polaris.api.exception.PolarisException;
import com.tencent.polaris.factory.api.APIFactory;
import com.tencent.polaris.factory.config.ConfigurationImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * PolarisConsumerAutoConfiguration.
 *
 * @author tenkye
 * @date 2021/11/19 9:08 下午
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(PolarisProperties.class)
public class PolarisConsumerAutoConfiguration {

    @Configuration
    @Import(PolarisConsumerScannerRegistrar.class)
    @ConditionalOnMissingBean(PolarisConsumerFactoryBean.class)
    public static class PolarisScanAutoConfiguration {


    }


}
