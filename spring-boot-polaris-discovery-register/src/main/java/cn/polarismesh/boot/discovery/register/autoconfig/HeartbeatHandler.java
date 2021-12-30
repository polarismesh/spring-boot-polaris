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

import com.tencent.polaris.api.core.ProviderAPI;
import com.tencent.polaris.api.rpc.InstanceHeartbeatRequest;
import com.tencent.polaris.client.util.NamedThreadFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HeartbeatHandler {

    private ScheduledExecutorService scheduledExecutorService;

    private final Map<InstanceKey, ScheduledFuture<?>> futures = new ConcurrentHashMap<>();

    @Autowired
    private ProviderAPI providerAPI;

    @PostConstruct
    public void init() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("polaris-heartbeat"));
    }

    @PreDestroy
    public void destroy() {
        scheduledExecutorService.shutdown();
    }

    @EventListener
    public void scheduleHeartbeat(InstanceRegisterEvent instanceRegisterEvent) {
        ScheduledFuture<?> future = scheduledExecutorService.scheduleWithFixedDelay(
                new HeartbeatTask(instanceRegisterEvent), instanceRegisterEvent.getTtl(),
                instanceRegisterEvent.getTtl(), TimeUnit.SECONDS);
        InstanceKey instanceKey = instanceRegisterEvent.getInstanceKey();
        futures.put(instanceKey, future);
        log.info("[Polaris] success to schedule heartbeat instance {}:{}, service is {}, namespace is {}",
                instanceKey.getHost(), instanceKey.getPort(), instanceKey.getService(), instanceKey.getNamespace());
    }

    @EventListener
    public void cancelHeartbeat(InstanceDeregisterEvent instanceDeregisterEvent) {
        InstanceKey instanceKey = instanceDeregisterEvent.getInstanceKey();
        ScheduledFuture<?> future = futures.get(instanceKey);
        if (null != future) {
            future.cancel(true);
        }
        log.info("[Polaris] success to remove schedule heartbeat instance {}:{}, service is {}, namespace is {}",
                instanceKey.getHost(), instanceKey.getPort(), instanceKey.getService(), instanceKey.getNamespace());
    }

    private class HeartbeatTask implements Runnable {

        private final InstanceRegisterEvent instanceRegisterEvent;

        public HeartbeatTask(InstanceRegisterEvent instanceRegisterEvent) {
            this.instanceRegisterEvent = instanceRegisterEvent;
        }

        @Override
        public void run() {
            InstanceHeartbeatRequest instanceHeartbeatRequest = new InstanceHeartbeatRequest();
            instanceHeartbeatRequest.setNamespace(instanceRegisterEvent.getInstanceKey().getNamespace());
            instanceHeartbeatRequest.setService(instanceRegisterEvent.getInstanceKey().getService());
            instanceHeartbeatRequest.setHost(instanceRegisterEvent.getInstanceKey().getHost());
            instanceHeartbeatRequest.setPort(instanceRegisterEvent.getInstanceKey().getPort());
            instanceHeartbeatRequest.setToken(instanceRegisterEvent.getToken());
            try {
                HeartbeatHandler.this.providerAPI.heartbeat(instanceHeartbeatRequest);
            } catch (Throwable e) {
                log.error("[Polaris] fail to heartbeat instance {}:{}, service is {}, namespace is {}",
                        instanceHeartbeatRequest.getHost(), instanceHeartbeatRequest.getPort(),
                        instanceHeartbeatRequest.getService(), instanceHeartbeatRequest.getNamespace());
            }
        }
    }
}
