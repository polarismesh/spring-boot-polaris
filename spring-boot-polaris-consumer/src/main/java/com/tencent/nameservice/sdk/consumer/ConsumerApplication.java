package com.tencent.nameservice.sdk.consumer;

import com.tencent.nameservice.sdk.PolarisConsumerScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@PolarisConsumerScan("com.tencent.nameservice.sdk.consumer.remote")
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
