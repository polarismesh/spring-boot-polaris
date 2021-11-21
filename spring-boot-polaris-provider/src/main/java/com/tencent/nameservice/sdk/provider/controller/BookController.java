/*
 * Copyright (c) 2010-2030 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */

package com.tencent.nameservice.sdk.provider.controller;

import com.tencent.nameservice.sdk.common.model.Book;
import com.tencent.nameservice.sdk.common.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BookController.
 *
 * @author tenkye
 * @date 2021/11/19 10:05 下午
 */
@Slf4j
@RestController
public class BookController {

    @RequestMapping("/book/{id}")
    public R<Book> getBook(@PathVariable("id") String id) {

        log.info("Get book by id {}", id);

        return R.ok(new Book().setId(id));
    }
}
