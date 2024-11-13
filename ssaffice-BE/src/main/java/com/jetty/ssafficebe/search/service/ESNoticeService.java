package com.jetty.ssafficebe.search.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.search.payload.ESNoticeRequest;
import com.jetty.ssafficebe.search.payload.ESNoticeSearchFilter;
import org.springframework.data.domain.Page;

public interface ESNoticeService {

    ApiResponse saveNotice(ESNoticeRequest request);

    ApiResponse deleteNotice(Long noticeId);

    Page<?> searchGlobalNotice(Long userId, ESNoticeSearchFilter filter);
}
