package com.jetty.ssafficebe.notice.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;

public interface NoticeService {

    ApiResponse addNotice(NoticeRequest noticeRequest);
}
