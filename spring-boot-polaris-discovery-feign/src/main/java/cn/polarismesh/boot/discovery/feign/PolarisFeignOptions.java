/*
 * Copyright 2013-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.polarismesh.boot.discovery.feign;

import static cn.polarismesh.boot.context.PolarisContextConst.DEFAULT_NAMESPACE;
import static cn.polarismesh.boot.discovery.feign.PolarisFeignConst.DEFAULT_SCHEME;

public class PolarisFeignOptions {

    private String scheme = DEFAULT_SCHEME;

    private String namespace = DEFAULT_NAMESPACE;

    private String service;

    private PolarisFeignOptions() {

    }

    public static PolarisFeignOptions build() {
        return new PolarisFeignOptions();
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public PolarisFeignOptions withScheme(String scheme) {
        setScheme(scheme);
        return this;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public PolarisFeignOptions withNamespace(String namespace) {
        setNamespace(namespace);
        return this;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public PolarisFeignOptions withService(String service) {
        setService(service);
        return this;
    }
}
