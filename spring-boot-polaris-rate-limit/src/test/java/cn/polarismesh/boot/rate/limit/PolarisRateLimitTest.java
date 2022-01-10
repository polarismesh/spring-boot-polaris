package cn.polarismesh.boot.rate.limit;

import cn.polarismesh.boot.rate.limit.app.TestApplication;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author quicksand - 2022/1/10
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TestApplication.class)
//@AutoConfigureMockMvc
//public class PolarisRateLimitTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    public void testBase() throws Exception {
//        String url = "/hello";
//        MvcResult mvcResult  = mvc.perform(get(url)).andReturn();
//        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(),"hello");
//    }
//
//}
