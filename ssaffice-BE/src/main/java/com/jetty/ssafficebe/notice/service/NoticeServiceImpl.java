package com.jetty.ssafficebe.notice.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.converter.NoticeConverter;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeConverter noticeConverter;

    @Transactional
    @Override
    public ApiResponse addNotice(NoticeRequest noticeRequest) {
        Notice notice = noticeConverter.toNotice(noticeRequest);

        Notice savedNotice = noticeRepository.save(notice);
        return new ApiResponse(true, HttpStatus.CREATED, "공지사항 추가 성공", savedNotice.getTitle());
    }
}