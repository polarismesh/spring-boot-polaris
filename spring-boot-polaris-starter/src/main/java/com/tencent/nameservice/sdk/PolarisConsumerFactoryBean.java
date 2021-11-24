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
import com.tencent.polaris.factory.api.APIFactory;
import com.tencent.polaris.factory.config.ConfigurationImpl;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * PolarisConsumerFactoryBean.
 *
 * @author tenkye
 * @date 2021/11/19 9:15 下午
 */
@Slf4j
public class PolarisConsumerFactoryBean<T> implements FactoryBean {

    static {

        ConfigurationImpl configuration = new ConfigurationImpl();
        configuration.setDefault(); //使用默认配置
        ConsumerAPI consumer = null;
        try {
            consumer = APIFactory.createConsumerAPIByConfig(configuration);
            Runtime.getRuntime().addShutdownHook(new Thread(consumer::destroy));

            ConsumerAPIHolder.hold(consumer);

        } catch (PolarisException e) {

            log.error("Init ConsumerAPI error", e);
        }

    }

    @Resource
    private PolarisProperties polarisProperties;

    private Class<?> mapperClass;

    // 这里的mapperClass就是Mapper接口的class
    public PolarisConsumerFactoryBean(Class<?> mapperClass) {
        this.mapperClass = mapperClass;
    }

    @Override
    public Class<?> getObjectType() {
        return mapperClass;
    }

    @Override
    public Object getObject() {

        PolarisConsumer consumer = AnnotationUtils.getAnnotation(mapperClass, PolarisConsumer.class);
        assert consumer != null;
        String id = consumer.id();
        Assert.notNull(id, "The attribute id for this PolarisConsumer can't blank.");

        ConsumerProperties properties = polarisProperties.getConsumer().get(id);
        if (null == properties) {
            properties = new ConsumerProperties();
        }
        String namespace = consumer.namespace();
        if (StringUtils.hasText(namespace)) {
            properties.setNamespace(namespace);
        }
        String service = consumer.service();
        if (StringUtils.hasText(service)) {
            properties.setService(service);
        }
        String version = consumer.version();
        if (StringUtils.hasText(version)) {
            properties.setVersion(version);
        }

        return Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(PolarisTarget.create(mapperClass, properties));
    }
}
