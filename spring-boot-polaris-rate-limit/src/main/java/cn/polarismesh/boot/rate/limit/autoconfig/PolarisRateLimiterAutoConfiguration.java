package cn.polarismesh.boot.rate.limit.autoconfig;

import cn.polarismesh.boot.context.PolarisContextConst;
import cn.polarismesh.boot.rate.limit.PolarisRateLimitHandlerInterceptor;
import cn.polarismesh.boot.rate.limit.PolarisRateLimitInterceptorConfig;
import cn.polarismesh.boot.rate.limit.properties.PolarisRateLimitProperties;
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
@ConditionalOnProperty(prefix = PolarisContextConst.PREFIX, name = "rate-limit.enable", havingValue = "true")
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
    public PolarisRateLimitHandlerInterceptor rateLimitHandlerInterceptor(LimitAPI limitAPI) {
        return new PolarisRateLimitHandlerInterceptor(limitAPI, polarisRateLimitProperties);
    }

    @Bean
    public PolarisRateLimitInterceptorConfig interceptorConfig(
        PolarisRateLimitHandlerInterceptor polarisRateLimitHandlerInterceptor) {
        return new PolarisRateLimitInterceptorConfig(polarisRateLimitHandlerInterceptor);
    }

}
