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

import org.springframework.context.ApplicationEvent;

public class InstanceRegisterEvent extends ApplicationEvent {

    private final InstanceKey instanceKey;

    private final int ttl;

    public InstanceRegisterEvent(Object source, InstanceKey instanceKey, int ttl) {
        super(source);
        this.instanceKey = instanceKey;
        this.ttl = ttl;
    }

    public InstanceKey getInstanceKey() {
        return instanceKey;
    }

    public int getTtl() {
        return ttl;
    }
}
