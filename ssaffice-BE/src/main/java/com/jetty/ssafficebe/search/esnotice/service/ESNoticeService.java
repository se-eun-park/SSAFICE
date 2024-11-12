package com.jetty.ssafficebe.search.esnotice.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.search.esnotice.payload.ESNoticeRequest;

public interface ESNoticeService {

    ApiResponse saveNotice(ESNoticeRequest request);

    ApiResponse deleteNotice(Long noticeId);
}
