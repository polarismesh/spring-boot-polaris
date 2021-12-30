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

import com.tencent.polaris.client.util.NamedThreadFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HeartbeatHandler {

    private ScheduledExecutorService scheduledExecutorService;

    private final Map<InstanceKey, ScheduledFuture<?>> futures = new ConcurrentHashMap<>();

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
        ScheduledFuture<?> future = scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {

            }
        }, instanceRegisterEvent.getTtl(), instanceRegisterEvent.getTtl(), TimeUnit.SECONDS);
        futures.put(instanceRegisterEvent.getInstanceKey(), future);
    }

    @EventListener
    public void cancelHeartbeat(InstanceDeregisterEvent instanceDeregisterEvent) {
        ScheduledFuture<?> future = futures.get(instanceDeregisterEvent.getInstanceKey());
        if (null != future) {
            future.cancel(true);
        }
    }


}
