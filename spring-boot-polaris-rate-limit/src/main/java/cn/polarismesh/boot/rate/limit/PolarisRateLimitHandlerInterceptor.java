package cn.polarismesh.boot.rate.limit;

import cn.polarismesh.boot.rate.limit.properties.PolarisRateLimitProperties;
import com.tencent.polaris.api.core.ProviderAPI;
import com.tencent.polaris.api.pojo.Service;
import com.tencent.polaris.api.pojo.ServiceEventKeysProvider;
import com.tencent.polaris.client.api.SDKContext;
import com.tencent.polaris.ratelimit.api.core.LimitAPI;
import com.tencent.polaris.ratelimit.api.rpc.QuotaRequest;
import com.tencent.polaris.ratelimit.api.rpc.QuotaResponse;
import com.tencent.polaris.ratelimit.api.rpc.QuotaResultCode;
import com.tencent.polaris.ratelimit.factory.LimitAPIFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * @author quicksand - 2022/1/10
 */
public class PolarisRateLimitHandlerInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(HandlerInterceptor.class);

    private final LimitAPI limitAPI;

    private final PolarisRateLimitProperties polarisRateLimitProperties;

    public PolarisRateLimitHandlerInterceptor(LimitAPI limitAPI,
        PolarisRateLimitProperties polarisRateLimitProperties) {
        this.limitAPI = limitAPI;
        this.polarisRateLimitProperties = polarisRateLimitProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        QuotaRequest quotaRequest = new QuotaRequest();
        quotaRequest.setNamespace(polarisRateLimitProperties.getNamespace());
        quotaRequest.setService(polarisRateLimitProperties.getApplicationName());
        quotaRequest.setMethod(request.getRequestURI());
        quotaRequest.setLabels(polarisRateLimitProperties.getLabels());
        QuotaResponse quotaResponse = limitAPI.getQuota(quotaRequest);

        return QuotaResultCode.QuotaResultOk.equals(quotaResponse.getCode());
    }
}
