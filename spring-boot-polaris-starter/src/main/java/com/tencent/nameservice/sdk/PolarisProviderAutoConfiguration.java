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

import com.tencent.nameservice.sdk.PolarisProperties.ProviderProperties;
import com.tencent.nameservice.sdk.PolarisProviderAutoConfiguration.EnableProviderFlag;
import com.tencent.polaris.api.core.ProviderAPI;
import com.tencent.polaris.api.exception.PolarisException;
import com.tencent.polaris.api.rpc.InstanceRegisterRequest;
import com.tencent.polaris.api.rpc.InstanceRegisterResponse;
import com.tencent.polaris.factory.api.APIFactory;
import com.tencent.polaris.factory.config.ConfigurationImpl;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * PolarisProviderAutoConfiguration.
 *
 * @author tenkye
 * @date 2021/11/20 8:43 下午
 */
@Slf4j
@ConditionalOnBean(EnableProviderFlag.class)
@Configuration
@EnableConfigurationProperties(PolarisProperties.class)
public class PolarisProviderAutoConfiguration {

    @Resource
    private PolarisProperties polarisProperties;

    /**
     * 注册.
     */
    @Bean
    public ProviderAPI providerAPI() throws PolarisException {

        ConfigurationImpl configuration = new ConfigurationImpl(); // 创建配置
        configuration.setDefault(); // 使用默认配置
        ProviderAPI provider = APIFactory.createProviderAPIByConfig(configuration); // 创建providerAPI

        Runtime.getRuntime().addShutdownHook(new Thread(provider::destroy));

        return provider;
    }

    @Bean
    ProviderRegister providerRegister(ProviderAPI providerAPI) throws PolarisException {
        return new ProviderRegister(providerAPI, polarisProperties);

    }

    static class ProviderRegister {

        ProviderRegister(ProviderAPI providerAPI, PolarisProperties polarisProperties)
                throws PolarisException {

            ProviderProperties provider = polarisProperties.getProvider();

            InstanceRegisterRequest request = new InstanceRegisterRequest();
            request.setNamespace(provider.getNamespace());
            request.setService(provider.getService());
            if (StringUtils.hasText(provider.getHost())) {
                request.setHost(provider.getHost());
            } else {
                request.setHost(IpUtil.getLocalIP());
            }
            request.setPort(provider.getPort());
            if (null != provider.getTtl()) {
                request.setTtl(provider.getTtl());
            }
            request.setToken(provider.getToken()); //设置token
            if (StringUtils.hasText(provider.getVersion())) {
                request.setVersion(provider.getVersion());
            }

            InstanceRegisterResponse instanceRegisterResponse = providerAPI.register(request);

            log.info(instanceRegisterResponse.toString());
        }
    }

    static class EnableProviderFlag {


    }
}
