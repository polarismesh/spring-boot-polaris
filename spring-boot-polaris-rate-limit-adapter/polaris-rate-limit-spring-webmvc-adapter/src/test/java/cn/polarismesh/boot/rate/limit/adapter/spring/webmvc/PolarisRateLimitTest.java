package cn.polarismesh.boot.rate.limit.adapter.spring.webmvc;

import cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.app.TestApplication;
import com.google.protobuf.Duration;
import com.google.protobuf.StringValue;
import com.google.protobuf.UInt32Value;
import com.tencent.polaris.api.pojo.ServiceKey;
import com.tencent.polaris.client.pb.ModelProto;
import com.tencent.polaris.client.pb.RateLimitProto;
import com.tencent.polaris.test.mock.discovery.NamingServer;
import java.io.IOException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * need register public polaris
 *
 * @author quicksand - 2022/1/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
public class PolarisRateLimitTest {

    @Autowired
    private MockMvc mvc;

    @BeforeClass
    public static void init() throws IOException {
        NamingServer namingServer = NamingServer.startNamingServer(8091);
        ServiceKey serviceKey = new ServiceKey("default", "rate-limit");
        namingServer.getNamingService().addService(serviceKey);
        RateLimitProto.Rule.Builder ruleBuilder = RateLimitProto.Rule.newBuilder();
        ruleBuilder.setType(RateLimitProto.Rule.Type.LOCAL);
        ruleBuilder.setPriority(UInt32Value.newBuilder().setValue(0).build());
        ruleBuilder.setAction(StringValue.newBuilder().setValue("reject").build());
        ruleBuilder.setAmountMode(RateLimitProto.Rule.AmountMode.GLOBAL_TOTAL);
        // 1秒内允许1次请求
        ruleBuilder.addAmounts(
            RateLimitProto.Amount.newBuilder().setMaxAmount(UInt32Value.newBuilder().setValue(1).build()).setValidDuration(
                Duration.newBuilder().setSeconds(1).build()));
        RateLimitProto.RateLimit.Builder rateLimitBuilder = RateLimitProto.RateLimit.newBuilder();
        rateLimitBuilder.addRules(ruleBuilder.build());
        namingServer.getNamingService().setRateLimit(serviceKey, rateLimitBuilder.build());
    }

    @Test
    public void testRateLimit() throws Exception {

        String url = "/hello";
        mvc.perform(get(url)).andReturn();
        mvc.perform(get(url)).andExpect(status().isTooManyRequests());
    }

}
