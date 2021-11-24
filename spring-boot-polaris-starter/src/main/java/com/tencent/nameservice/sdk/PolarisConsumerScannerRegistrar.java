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

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * PolarisConsumerScannerRegistrar.
 *
 * @author tenkye
 * @date 2021/11/19 9:14 下午
 */
@Slf4j
public class PolarisConsumerScannerRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware,
        ResourceLoaderAware {

    private BeanFactory beanFactory;
    private ResourceLoader resourceLoader;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        List<String> packages;

        try {

            AnnotationAttributes annoAttrs = AnnotationAttributes
                    .fromMap(importingClassMetadata.getAnnotationAttributes(PolarisConsumerScan.class.getName()));
            if (annoAttrs != null) {
                // 使用PolarisConsumerScan进行扫描
                List<String> basePackages = new ArrayList<>();
                for (String pkg : annoAttrs.getStringArray("value")) {
                    if (StringUtils.hasText(pkg)) {
                        basePackages.add(pkg);
                    }
                }
                packages = basePackages;

            } else {

                // 默认扫描路径扫描
                packages = AutoConfigurationPackages.get(this.beanFactory);
            }

            PolarisConsumerScanner scanner = new PolarisConsumerScanner(registry);
            scanner.setResourceLoader(this.resourceLoader);
            // 设置要扫描的接口注解，这里就相当于Mapper注解
            scanner.setAnnotationClass(PolarisConsumer.class);
            scanner.registerFilters();
            scanner.doScan(StringUtils.toStringArray(packages));
        } catch (IllegalStateException e) {
            log.error("registerBeanDefinitions error", e);
        }
    }
}
