/*
 * Tencent is pleased to support the open source community by making Spring Cloud Tencent available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package cn.polarismesh.boot.context;

import com.tencent.polaris.api.exception.PolarisException;
import com.tencent.polaris.client.api.SDKContext;
import com.tencent.polaris.factory.config.ConfigurationImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for Polaris {@link SDKContext}
 *
 * @author Haotian Zhang
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(PolarisContextProperties.class)
public class PolarisContextAutoConfiguration {

    @Bean(name = "sdkContext", initMethod = "init", destroyMethod = "destroy")
    @ConditionalOnMissingBean
    public SDKContext sdkContext(ObjectProvider<List<PolarisConfigModifier>> modifierListProvider) throws PolarisException {
        PolarisConfigurationFactory configurationFactory = new PolarisConfigurationFactory(modifierListProvider.getIfUnique());
        return SDKContext.initContextByConfig(configurationFactory.configuration());
    }

    @Bean
    @ConditionalOnMissingBean
    public PolarisConfigModifier polarisConfigModifier(PolarisContextProperties properties) {
        return new ModifyAddress(properties);
    }

    private static class ModifyAddress implements PolarisConfigModifier {

        private final PolarisContextProperties properties;

        public ModifyAddress(PolarisContextProperties properties) {
            this.properties = properties;
        }

        @Override
        public void modify(ConfigurationImpl configuration) {
            String addressStr = properties.getAddress();
            List<String> addresses = new ArrayList<>();
            if (StringUtils.hasText(addressStr)) {
                String[] tokens = StringUtils.split(addressStr, PolarisContextConst.ADDRESS_SEP);
                if (null != tokens) {
                    for (String token : tokens) {
                        URI uri = URI.create(token);
                        addresses.add(uri.getAuthority());
                    }
                } else {
                    URI uri = URI.create(addressStr);
                    addresses.add(uri.getAuthority());
                }
            }
            configuration.getGlobal().getServerConnector().setAddresses(addresses);
        }
    }

}
