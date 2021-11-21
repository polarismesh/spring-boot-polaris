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

import com.tencent.nameservice.sdk.PolarisProperties.ConsumerProperties;
import com.tencent.polaris.api.core.ConsumerAPI;
import com.tencent.polaris.api.exception.PolarisException;
import com.tencent.polaris.api.pojo.Instance;
import com.tencent.polaris.api.rpc.GetOneInstanceRequest;
import com.tencent.polaris.api.rpc.InstancesResponse;
import feign.Request;
import feign.RequestTemplate;
import feign.Target;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 * PolarisTarget.
 *
 * @author tenkye
 * @date 2021/6/1 2:26 下午
 */
@Slf4j
public class PolarisTarget<T> implements Target<T> {

    private ConsumerAPI consumerAPI;
    private final Class<T> clazz;
    private final ConsumerProperties consumerProperties;

    private PolarisTarget(Class<T> clazz, ConsumerProperties consumerProperties) {
        this.consumerAPI = ConsumerAPIHolder.get();
        this.clazz = clazz;
        this.consumerProperties = consumerProperties;
    }

    public static <T> PolarisTarget<T> create(Class<T> clazz,
            ConsumerProperties polarisConsumerProperties) {

        return new PolarisTarget<T>(clazz, polarisConsumerProperties);
    }


    @Override
    public Class<T> type() {
        return clazz;
    }

    @Override
    public String name() {
        try {
            return choose().getHost();
        } catch (PolarisException e) {
            throw new BizException(e.getMessage());
        }
    }

    @Override
    public String url() {
        return null;
    }

    @Override
    public Request apply(RequestTemplate input) {

        Instance instance = null;
        try {
            instance = choose();
        } catch (PolarisException e) {
            throw new BizException(e.getMessage());
        }
        String url = String.format("%s://%s:%s", consumerProperties.getScheme(),
                instance.getHost(), instance.getPort());
        input.target(url);

        return input.request();
    }

    private Instance choose() throws PolarisException {

        log.info("Get address from polaris...");

        GetOneInstanceRequest getInstancesRequest = new GetOneInstanceRequest();
        getInstancesRequest.setNamespace(consumerProperties.getNamespace());
        getInstancesRequest.setService(consumerProperties.getService());
        InstancesResponse instances = consumerAPI.getOneInstance(getInstancesRequest);

        return Arrays.stream(instances.getInstances()).findFirst()
                .orElseThrow(() -> new BizException("查找北极星失败"));
    }


}
