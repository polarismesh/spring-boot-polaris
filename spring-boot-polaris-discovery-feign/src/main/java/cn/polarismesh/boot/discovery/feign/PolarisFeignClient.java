/*
 * Tencent is pleased to support the open source community by making Spring Cloud Tencent available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package cn.polarismesh.boot.discovery.feign;


import com.tencent.polaris.api.core.ConsumerAPI;
import com.tencent.polaris.api.pojo.RetStatus;
import com.tencent.polaris.api.rpc.ServiceCallResult;
import feign.Client;
import feign.Request;
import feign.Request.Options;
import feign.Response;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class PolarisFeignClient implements Client {

    private final Client delegate;

    private final ConsumerAPI consumerAPI;

    public PolarisFeignClient(Client delegate, ConsumerAPI consumerAPI) {
        this.delegate = delegate;
        this.consumerAPI = consumerAPI;

    }

    @Override
    public Response execute(Request request, Options options) throws IOException {
        IOException ioException = null;
        Response response = null;
        long startTimeMilli = System.currentTimeMillis();
        try {
            response = delegate.execute(request, options);
        } catch (IOException e) {
            ioException = e;
        }
        long delay = System.currentTimeMillis() - startTimeMilli;
        reportExecuteResult(request, ioException, response, delay);
        if (null != ioException) {
            throw ioException;
        }
        return response;
    }

    private void reportExecuteResult(Request request, IOException throwable, Response response, long delay) {
        String namespace = null;
        String service = null;
        Collection<String> namespaces = request.headers().get(PolarisFeignConst.HEADER_NAMESPACE);
        if (!CollectionUtils.isEmpty(namespaces)) {
            namespace = namespaces.stream().iterator().next();
        }
        Collection<String> services = request.headers().get(PolarisFeignConst.HEADER_SERVICE);
        if (!CollectionUtils.isEmpty(services)) {
            service = services.stream().iterator().next();
        }
        if (StringUtils.hasText(service) && StringUtils.hasText(namespace)) {
            String host = null;
            int port = 0;
            String urlStr = request.url();
            URI uri = URI.create(urlStr);
            String authority = uri.getAuthority();
            String[] tokens = StringUtils.split(authority, ":");
            if (null != tokens && tokens.length > 1) {
                host = tokens[0];
                port = Integer.parseInt(tokens[1]);
            }
            if (StringUtils.hasText(host) && port > 0) {
                ServiceCallResult serviceCallResult = new ServiceCallResult();
                serviceCallResult.setNamespace(namespace);
                serviceCallResult.setService(service);
                serviceCallResult.setHost(host);
                serviceCallResult.setPort(port);
                serviceCallResult.setRetStatus(null != throwable ? RetStatus.RetFail : RetStatus.RetSuccess);
                serviceCallResult.setRetCode(null != response ? response.status() : -1);
                serviceCallResult.setDelay(delay);
                consumerAPI.updateServiceCallResult(serviceCallResult);
            }
        }
    }
}
