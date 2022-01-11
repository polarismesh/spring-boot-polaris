/*
 * Copyright 1999-2020 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.app.controller;

import cn.polarismesh.boot.rate.limit.adapter.spring.webmvc.exception.RateLimitException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quicksand - 2022/1/10
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ExceptionHandler(RateLimitException.class)
    @ResponseBody
    public String rateLimitException(RateLimitException e) {
        return e.getMessage();
    }
}