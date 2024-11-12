package com.jetty.ssafficebe.search.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.search.converter.ESNoticeConverter;
import com.jetty.ssafficebe.search.document.ESNotice;
import com.jetty.ssafficebe.search.payload.ESNoticeRequest;
import com.jetty.ssafficebe.search.payload.ESNoticeSearchFilter;
import com.jetty.ssafficebe.search.repository.ESNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    // TODO : searchGlobalNotice 구현
    @Override
    public Page<?> searchGlobalNotice(Long userId, ESNoticeSearchFilter filter) {
        // 1. 사용자 아이디로 사용자가 속해있는 채널 아이디 조회
        // 2. 채널 아이디 리스트로 공지 filter 적용
        // 3. searchFilter 검색어 filter 적용
        // 4. DTO 변환하여 리턴
        return null;
    }
}