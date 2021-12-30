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

package cn.polarismesh.boot.discovery.register.autoconfig;

import cn.polarismesh.boot.discovery.register.properties.PolarisDiscoveryProperties;
import com.tencent.polaris.api.core.ProviderAPI;
import com.tencent.polaris.client.api.SDKContext;
import com.tencent.polaris.factory.api.DiscoveryAPIFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableConfigurationProperties(value = PolarisDiscoveryProperties.class)
@ConditionalOnClass(name = "org.springframework.boot.context.properties.bind.Binder")
public class PolarisDiscoveryAutoConfiguration {

    @Bean
    public ProviderAPI providerAPI(SDKContext sdkContext) {
        return DiscoveryAPIFactory.createProviderAPIByContext(sdkContext);
    }

    @Bean
    public PolarisDiscoveryAutoRegister discoveryAutoRegister() {
        return new PolarisDiscoveryAutoRegister();
    }

    @Bean
    public PolarisDiscoveryAutoDeregister discoveryAutoDeregister() {
        return new PolarisDiscoveryAutoDeregister();
    }
}
