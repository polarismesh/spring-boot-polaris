package cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.autoconfig;

import cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.PolarisRateLimitConst;
import cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.PolarisRateLimitWebInterceptor;
import cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.PolarisRateLimitWebInterceptorConfig;
import cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.properties.PolarisRateLimitProperties;
import com.tencent.polaris.client.api.SDKContext;
import com.tencent.polaris.ratelimit.api.core.LimitAPI;
import com.tencent.polaris.ratelimit.factory.LimitAPIFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author quicksand - 2022/1/10
 */
@Configuration
@ConditionalOnProperty(prefix = PolarisRateLimitConst.PREFIX, name = "enable", havingValue = "true")
@EnableConfigurationProperties(value = PolarisRateLimitProperties.class)
public class PolarisRateLimiterAutoConfiguration {

    private final PolarisRateLimitProperties polarisRateLimitProperties;

    public PolarisRateLimiterAutoConfiguration(PolarisRateLimitProperties polarisRateLimitProperties) {
        this.polarisRateLimitProperties = polarisRateLimitProperties;
    }

    @Bean
    public LimitAPI limitAPI(SDKContext sdkContext) {
        return LimitAPIFactory.createLimitAPIByContext(sdkContext);
    }

    @Bean
    public PolarisRateLimitWebInterceptor rateLimitHandlerInterceptor(LimitAPI limitAPI) {
        return new PolarisRateLimitWebInterceptor(limitAPI, polarisRateLimitProperties);
    }

    @Bean
    public PolarisRateLimitWebInterceptorConfig interceptorConfig(
        PolarisRateLimitWebInterceptor polarisRateLimitWebInterceptor) {
        return new PolarisRateLimitWebInterceptorConfig(polarisRateLimitWebInterceptor);
    }

}
