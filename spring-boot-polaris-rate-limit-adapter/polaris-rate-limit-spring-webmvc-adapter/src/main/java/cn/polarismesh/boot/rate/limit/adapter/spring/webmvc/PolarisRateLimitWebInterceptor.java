package cn.polarismesh.boot.rate.limit.adapter.spring.webmvc;

import cn.polarismesh.boot.context.PolarisContextConst;
import cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.exception.RateLimitException;
import cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.properties.PolarisRateLimitProperties;
import com.tencent.polaris.ratelimit.api.core.LimitAPI;
import com.tencent.polaris.ratelimit.api.rpc.QuotaRequest;
import com.tencent.polaris.ratelimit.api.rpc.QuotaResponse;
import com.tencent.polaris.ratelimit.api.rpc.QuotaResultCode;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author quicksand - 2022/1/10
 */
public class PolarisRateLimitWebInterceptor implements HandlerInterceptor {

    private final LimitAPI limitAPI;

    private final PolarisRateLimitProperties polarisRateLimitProperties;

    public PolarisRateLimitWebInterceptor(LimitAPI limitAPI,
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
        quotaRequest.setLabels(parseLabels(request));
        quotaRequest.setCount(1);
        QuotaResponse quotaResponse = limitAPI.getQuota(quotaRequest);
        boolean isLimit = QuotaResultCode.QuotaResultLimited.equals(quotaResponse.getCode());

        if (isLimit) {
            throw new RateLimitException("Too Many Requests");
        }
        return true;
    }

    protected Map<String, String> parseLabels(HttpServletRequest request) {
        Map<String, String> labelsMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            if (key.startsWith(PolarisContextConst.HEADER_RATE_LIMIT_LABELS_PREFIX)) {
                labelsMap.put(key, request.getHeader(key));
            }
        }
        return labelsMap;
    }

}
