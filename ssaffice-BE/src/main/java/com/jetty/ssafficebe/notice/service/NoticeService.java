package com.jetty.ssafficebe.notice.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface NoticeService {

    ApiResponse saveNotice(NoticeRequest noticeRequest);

    ApiResponse deleteNotice(Long noticeId);

    Page<NoticeSummaryForList> getNoticeList(Pageable pageable);
}
