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

import static cn.polarismesh.boot.context.PolarisContextConst.DEFAULT_LOCAL_HOST;

import com.tencent.polaris.api.config.ConfigProvider;
import com.tencent.polaris.api.config.Configuration;
import com.tencent.polaris.factory.ConfigAPIFactory;
import com.tencent.polaris.factory.config.ConfigurationImpl;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Properties for Polaris {@link com.tencent.polaris.client.api.SDKContext}
 *
 * @author Haotian Zhang
 */
@Component
@ConfigurationProperties(prefix = PolarisContextConst.PREFIX)
public class PolarisContextProperties {

    /**
     * polaris server 地址
     */
    private String address;

    @Autowired
    private List<PolarisConfigModifier> modifierList;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    protected Configuration configuration() {
        ConfigurationImpl configuration = (ConfigurationImpl) ConfigAPIFactory
                .defaultConfig(ConfigProvider.DEFAULT_CONFIG);
        configuration.setDefault();
        Collection<PolarisConfigModifier> modifiers = modifierList;
        if (!CollectionUtils.isEmpty(modifiers)) {
            for (PolarisConfigModifier modifier : modifiers) {
                modifier.modify(configuration);
            }
        }
        String defaultHost = getHost(configuration);
        configuration.getGlobal().getAPI().setBindIP(defaultHost);
        return configuration;
    }

    private String getHost(Configuration configuration) {
        String serverAddress = null;
        List<String> addresses = configuration.getGlobal().getServerConnector().getAddresses();
        if (!CollectionUtils.isEmpty(addresses)) {
            serverAddress = addresses.get(0);
        }
        if (!StringUtils.hasLength(serverAddress)) {
            return DEFAULT_LOCAL_HOST;
        }
        return getLocalHost(serverAddress);
    }

    private static String getLocalHost(String serverAddress) {
        String[] tokens = serverAddress.split(":");
        try (Socket socket = new Socket(tokens[0], Integer.parseInt(tokens[1]))) {
            return socket.getLocalAddress().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DEFAULT_LOCAL_HOST;
    }
}