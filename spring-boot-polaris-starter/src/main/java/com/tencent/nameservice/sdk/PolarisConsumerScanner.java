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

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * PolarisConsumerScanner.
 *
 * @author tenkye
 * @date 2021/11/19 9:22 下午
 */
public class PolarisConsumerScanner extends ClassPathBeanDefinitionScanner {

    private Class annotationClass;

    /**
     * Create a new {@code ClassPathBeanDefinitionScanner} for the given bean factory.
     *
     * @param registry the {@code BeanFactory} to load bean definitions into, in the form
     *         of a {@code BeanDefinitionRegistry}
     */
    public PolarisConsumerScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void registerFilters() {
        if (this.annotationClass != null) {
            this.addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
        }
    }

    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        // 其实还是交给Spring来帮我们做扫描
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            this.logger.warn("No PolarisConsumer was found in '" + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        } else {
            Iterator iterator = beanDefinitions.iterator();
            while (iterator.hasNext()) {
                BeanDefinitionHolder holder = (BeanDefinitionHolder) iterator.next();
                GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
                // 扫描到了以后，注意这里扫到的是接口，二实际注入到Spring容器的是PolarisConsumerFactoryBean
                // 因此，首先在构造中传如接口，然后设置下Bean的实际的class
                definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
                definition.setBeanClass(PolarisConsumerFactoryBean.class);
            }
        }
        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
}
