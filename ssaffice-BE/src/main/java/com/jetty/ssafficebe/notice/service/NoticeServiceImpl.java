package com.jetty.ssafficebe.notice.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.converter.NoticeConverter;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForList;
import com.jetty.ssafficebe.notice.repository.NoticeRepository;
import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.payload.CreatedBySummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ApiResponse saveNotice(NoticeRequest noticeRequest) {
        Notice notice = noticeConverter.toNotice(noticeRequest);
        Notice savedNotice = noticeRepository.save(notice);

        // TODO : 추가 시 개인별로 일정 추가 필요
        return new ApiResponse(true, HttpStatus.CREATED, "공지사항 추가 성공", savedNotice.getTitle());
    }

    @Transactional
    @Override
    public ApiResponse deleteNotice(Long noticeId) {
        noticeRepository.delete(noticeRepository.findById(noticeId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.NOTICE_NOT_FOUND, "해당 공지사항을 찾을 수 없습니다.", noticeId)));

        return new ApiResponse(true, HttpStatus.OK, "공지사항 삭제 성공", noticeId);
    }

    @Override
    public Page<NoticeSummaryForList> getNoticeList(Pageable pageable) {
        Page<Notice> noticeList = noticeRepository.getNoticeList(pageable);

        return noticeList.map(notice ->
                              {
                                  NoticeSummaryForList noticeSummaryForList = noticeConverter.toNoticeSummaryForList(
                                          notice);
                                  User createUser = notice.getCreateUser();

                                  if (createUser != null) {
                                      noticeSummaryForList.setCreateUser(CreatedBySummary.builder()
                                                                                         .userId(createUser.getUserId())
                                                                                         .name(createUser.getName())
                                                                                         .email(createUser.getEmail())
                                                                                         .profileImgUrl(
                                                                                                 createUser.getProfileImgUrl())
                                                                                         .build());
                                  }

                                  return noticeSummaryForList;
                              }
        );
    }
}