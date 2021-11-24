/*
 * Copyright (c) 2010-2030 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */

package com.tencent.nameservice.sdk.consumer.remote;

import com.tencent.nameservice.sdk.PolarisConsumer;
import com.tencent.nameservice.sdk.common.model.Book;
import com.tencent.nameservice.sdk.common.model.R;
import feign.Param;
import feign.RequestLine;

/**
 * BookClient.
 *
 * @author tenkye
 * @date 2021/11/19 9:37 下午
 */
@PolarisConsumer(id = "book")
public interface BookClient {

    @RequestLine("GET /book/{id}")
    R<Book> getBook(@Param("id") String id);

}
