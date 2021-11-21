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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * PolarisConsumer.
 *
 * @author tenkye
 * @date 2021/11/19 9:28 下午
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PolarisConsumer {

    /**
     * The uniq id of this Consumer, which can be associated with the configuration key <code>spring.polaris.consumer.xxx</code>.
     *
     * @return Consumer's id
     */
    String id();

    /**
     * The namespace of the service provider.
     *
     * @return namesapce
     */
    String namespace() default "";

    /**
     * The service name of the service provider.
     *
     * @return service name of provider
     */
    String service() default "";

    /**
     * The version number of the service provider.
     *
     * @return the version
     */
    String version() default "";
}
