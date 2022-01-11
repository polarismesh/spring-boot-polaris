/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
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
package cn.polarismesh.boot.rate.limit.adapter.spring.webmvc;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author quicksand - 2022/1/10
 */
public class PolarisRateLimitWebInterceptorConfig implements WebMvcConfigurer {

    private final PolarisRateLimitWebInterceptor polarisRateLimitWebInterceptor;

    public PolarisRateLimitWebInterceptorConfig(PolarisRateLimitWebInterceptor polarisRateLimitWebInterceptor) {
        this.polarisRateLimitWebInterceptor = polarisRateLimitWebInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //Add rate-limit interceptor
        registry.addInterceptor(polarisRateLimitWebInterceptor).addPathPatterns("/**");
    }

}
