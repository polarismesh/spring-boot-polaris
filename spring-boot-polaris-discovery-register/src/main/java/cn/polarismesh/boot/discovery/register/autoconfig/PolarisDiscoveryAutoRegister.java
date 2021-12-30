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

import cn.polarismesh.boot.context.PolarisContextConst;
import cn.polarismesh.boot.discovery.register.properties.PolarisDiscoveryProperties;
import com.tencent.polaris.api.core.ProviderAPI;
import com.tencent.polaris.api.rpc.InstanceRegisterRequest;
import com.tencent.polaris.client.api.SDKContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.security.jca.GetInstance.Instance;

@Slf4j
@Component
public class PolarisDiscoveryAutoRegister implements ApplicationListener<WebServerInitializedEvent> {

    @Autowired
    private SDKContext sdkContext;

    @Autowired
    private ProviderAPI providerAPI;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private PolarisDiscoveryProperties polarisDiscoveryProperties;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        if (!polarisDiscoveryProperties.isEnable()) {
            return;
        }
        InstanceRegisterRequest registerRequest = new InstanceRegisterRequest();
        registerRequest.setPort(polarisDiscoveryProperties.getPort());
        registerRequest.setService(polarisDiscoveryProperties.getApplicationName());
        if (StringUtils.hasLength(polarisDiscoveryProperties.getHost())) {
            registerRequest.setHost(polarisDiscoveryProperties.getHost());
        } else {
            registerRequest.setHost(sdkContext.getConfig().getGlobal().getAPI().getBindIP());
        }
        if (StringUtils.hasLength(polarisDiscoveryProperties.getNamespace())) {
            registerRequest.setNamespace(polarisDiscoveryProperties.getNamespace());
        } else {
            registerRequest.setNamespace(PolarisContextConst.DEFAULT_NAMESPACE);
        }
        if (StringUtils.hasLength(polarisDiscoveryProperties.getToken())) {
            registerRequest.setToken(polarisDiscoveryProperties.getToken());
        }
        registerRequest.setTtl(polarisDiscoveryProperties.getTtl());
        if (null != polarisDiscoveryProperties.getWeight()) {
            registerRequest.setWeight(polarisDiscoveryProperties.getWeight());
        }
        registerRequest.setMetadata(polarisDiscoveryProperties.getMetadata());

        providerAPI.register(registerRequest);
        log.info("[Polaris] success to register instance {}:{}, service is {}, namespace is {}",
                registerRequest.getHost(), registerRequest.getPort(), registerRequest.getService(),
                registerRequest.getNamespace());
        InstanceKey instanceKey = new InstanceKey(registerRequest.getNamespace(), registerRequest.getService(),
                registerRequest.getHost(), registerRequest.getPort());
        applicationContext.publishEvent(
                new InstanceRegisterEvent(this, instanceKey, registerRequest.getTtl()));
    }
}
