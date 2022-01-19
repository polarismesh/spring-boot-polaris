package cn.polarismesh.boot.ratelimit.spring.webmvc;

import cn.polarismesh.boot.context.PolarisContextConst;
import cn.polarismesh.boot.ratelimit.spring.webmvc.exception.RateLimitException;
import cn.polarismesh.boot.ratelimit.spring.webmvc.properties.PolarisRateLimitProperties;
import com.tencent.polaris.ratelimit.api.core.LimitAPI;
import com.tencent.polaris.ratelimit.api.rpc.QuotaRequest;
import com.tencent.polaris.ratelimit.api.rpc.QuotaResponse;
import com.tencent.polaris.ratelimit.api.rpc.QuotaResultCode;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
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
        try{
            return isLimit(request);
        }catch (RateLimitException e){
            handleRateLimitException(request,response,e);
            return false;
        }
    }

    protected boolean isLimit(HttpServletRequest request) throws RateLimitException {
        QuotaRequest quotaRequest = new QuotaRequest();
        quotaRequest.setNamespace(polarisRateLimitProperties.getNamespace());
        quotaRequest.setService(polarisRateLimitProperties.getApplicationName());
        quotaRequest.setMethod(request.getPathInfo());
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
        if (!StringUtils.hasText(polarisRateLimitProperties.getLabelsPrefixKey())) {
            return null;
        }
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

    protected void handleRateLimitException(HttpServletRequest request, HttpServletResponse response, RateLimitException e) throws Exception {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        PrintWriter out = response.getWriter();
        out.print(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
        out.flush();
        out.close();
    }

}
