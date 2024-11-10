package com.jetty.ssafficebe.notice.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.payload.NoticeDetail;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForList;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface NoticeService {

    ApiResponse saveNotice(NoticeRequest noticeRequest, List<MultipartFile> files) throws IOException;

    ApiResponse deleteNotice(Long noticeId);

    Page<NoticeSummaryForList> getNoticeList(Long userId, String usage, Pageable pageable);

    NoticeDetail getNotice(Long userId, Long noticeId);
}
