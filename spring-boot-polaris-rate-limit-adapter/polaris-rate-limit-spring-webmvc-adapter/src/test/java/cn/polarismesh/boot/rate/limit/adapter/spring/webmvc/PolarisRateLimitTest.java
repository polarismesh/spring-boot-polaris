package cn.polarismesh.boot.rate.limit.adapter.spring.webmvc;

import cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.app.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * need register public polaris
 *
 * @author quicksand - 2022/1/10
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TestApplication.class)
//@AutoConfigureMockMvc
public class PolarisRateLimitTest {

    //@Autowired
    private MockMvc mvc;

    //@Test
    public void testRateLimit() throws Exception {
        String url = "/hello";
        mvc.perform(get(url)).andReturn();
        mvc.perform(get(url)).andExpect(status().isOk()).andExpect(content().string("Too Many Requests"));
    }

}
