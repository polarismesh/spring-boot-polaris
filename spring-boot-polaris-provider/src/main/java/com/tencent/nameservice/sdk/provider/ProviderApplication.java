package com.tencent.nameservice.sdk.provider;

import com.tencent.nameservice.sdk.EnablePolarisProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnablePolarisProvider
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

}
