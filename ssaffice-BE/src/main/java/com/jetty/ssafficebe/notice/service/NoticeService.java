package com.jetty.ssafficebe.notice.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;

public interface NoticeService {

    ApiResponse saveNotice(NoticeRequest noticeRequest);

    ApiResponse deleteNotice(Long noticeId);
}
