package com.jetty.ssafficebe.search.esnotice.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.search.esnotice.converter.ESNoticeConverter;
import com.jetty.ssafficebe.search.esnotice.document.ESNotice;
import com.jetty.ssafficebe.search.esnotice.payload.ESNoticeRequest;
import com.jetty.ssafficebe.search.esnotice.repository.ESNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ESNoticeServiceImpl implements ESNoticeService {

    private final ESNoticeRepository esNoticeRepository;
    private final ESNoticeConverter esNoticeConverter;

    @Override
    public ApiResponse saveNotice(ESNoticeRequest request) {

        ESNotice esNotice = esNoticeConverter.toESNotice(request);
        ESNotice saved = esNoticeRepository.save(esNotice);

        return new ApiResponse(true, "ES에 추가 성공", saved.getNoticeId());
    }

    @Override
    public ApiResponse deleteNotice(Long noticeId) {
        esNoticeRepository.deleteById(noticeId);
        return new ApiResponse(true, "삭제 성공", noticeId);
    }
}