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

package cn.polarismesh.boot.ratelimit.spring.webmvc.properties;

import cn.polarismesh.boot.ratelimit.spring.webmvc.PolarisRateLimitConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author quicksand - 2022/1/10
 */
@ConfigurationProperties(prefix = PolarisRateLimitConst.PREFIX)
public class PolarisRateLimitProperties {

    @Value("${polaris.discovery.register.namespace:default}")
    private String namespace;

    @Value("${spring.application.name:}")
    private String applicationName;

    private String labelsPrefixKey;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getLabelsPrefixKey() {
        return labelsPrefixKey;
    }

    public void setLabelsPrefixKey(String labelsPrefixKey) {
        this.labelsPrefixKey = labelsPrefixKey;
    }
}
