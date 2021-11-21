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

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * PolarisProperties.
 *
 * @author tenkye
 * @date 2021/6/1 11:36 上午
 */
@Data
@ConfigurationProperties(prefix = "spring.polaris")
public class PolarisProperties {

    /**
     * Config the provider.
     */
    private ProviderProperties provider = new ProviderProperties();

    /**
     * Config the consumer，The key represents the unique mark of the remote service class.
     */
    private Map<String, ConsumerProperties> consumer = new HashMap<>();

    @Data
    static class ProviderProperties {

        /**
         * The namespace which you created in Polaris consoler, you must configure one.
         */
        private String namespace;

        /**
         * The service name which you created in Polaris consoler.
         */
        private String service;

        /**
         * You can specify an IP to deregister to the Polaris service，otherwise the first local IP from network card instead.
         */
        private String host;

        /**
         * You can specify the port address registered to the Polaris service，otherwise 8080 as default.
         */
        private Integer port = 8080;

        /**
         * Specify the token registered to the Polaris service, this is required.
         */
        private String token;

        /**
         * pecify the version number registered to the Polaris service.
         */
        private String version;

        /**
         * ttl.
         */
        private Integer ttl = 2;
    }


    @Data
    static class ConsumerProperties {

        /**
         * The protocol, default is http.
         */
        private String scheme = "http";

        /**
         * The namespace which you created in Polaris consoler, you must configure one.
         */
        private String namespace;

        /**
         * The service name which you created in Polaris consoler.
         */
        private String service;

        /**
         * pecify the version number registered to the Polaris service.
         */
        private String version;
    }

}
