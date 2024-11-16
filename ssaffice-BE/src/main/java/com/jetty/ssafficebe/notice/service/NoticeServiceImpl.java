package com.jetty.ssafficebe.notice.service;

import com.jetty.ssafficebe.channel.service.ChannelService;
import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidAuthorizationException;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidValueException;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.file.service.AttachmentFileService;
import com.jetty.ssafficebe.notice.converter.NoticeConverter;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.payload.NoticeDetail;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForList;
import com.jetty.ssafficebe.notice.repository.NoticeRepository;
import com.jetty.ssafficebe.schedule.service.ScheduleService;
import com.jetty.ssafficebe.user.converter.UserConverter;
import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.payload.CreatedBySummary;
import com.jetty.ssafficebe.user.payload.UserSummary;
import com.jetty.ssafficebe.user.service.UserService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeConverter noticeConverter;
    private final AttachmentFileService attachmentFileService;

    private final ScheduleService scheduleService;

    private final UserConverter userConverter;
    private final UserService userService;

    @Transactional
    @Override
    public ApiResponse saveNotice(NoticeRequest noticeRequest, List<MultipartFile> files) throws IOException {
        Notice notice = noticeConverter.toNotice(noticeRequest);
        Notice savedNotice = noticeRepository.save(notice);

        // 파일 업로드
        for (MultipartFile file : files) {
            attachmentFileService.uploadFile(file, "notice", savedNotice.getNoticeId());
        }

        // TODO : 추가 시 개인별로 일정 추가 필요
        // 공지 대상자 일정 추가
        // 1. 채널 아이디로 공지 대상자 조회
        List<UserSummary> usersByChannelId = userService.getUsersByChannelId(noticeRequest.getChannelId());

        List<Long> userIds = usersByChannelId.stream().map(UserSummary::getUserId).toList();

        // 2. 공지 대상자 일정 추가
        scheduleService.saveSchedulesFromNotice(savedNotice, userIds);

        return new ApiResponse(true, HttpStatus.CREATED, "공지사항 추가 성공", savedNotice.getTitle());
    }

    @Transactional
    @Override
    public ApiResponse deleteNotice(Long userId, Long noticeId) {
        // 공지Id로 공지사항 조회
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.NOTICE_NOT_FOUND, "해당 공지사항을 찾을 수 없습니다.", noticeId));

        // 공지사항 주인인지 확인
        if (!notice.getCreatedBy().equals(userId)) {
            throw new InvalidAuthorizationException(ErrorCode.INVALID_AUTHORIZATION, "userId", userId);
        }

        noticeRepository.delete(notice);

        return new ApiResponse(true, HttpStatus.OK, "공지사항 삭제 성공", noticeId);
    }

    @Override
    public Page<NoticeSummaryForList> getNoticeList(Long userId, String usage, Pageable pageable) {
        Page<Notice> noticeList;

        // ROLE_USER인 경우 해당 유저가 속해있는 채널의 공지사항만 조회
        if (usage.equals("GLOBAL_NOTICE")) {
            noticeList = noticeRepository.getNoticeList(userId, pageable);
        } else {
            throw new InvalidValueException(ErrorCode.INVALID_USAGE, "usage", usage);
        }

        return noticeList.map(notice -> {
                                  NoticeSummaryForList noticeSummaryForList = noticeConverter.toNoticeSummaryForList(
                                          notice);

                                  User createUser = notice.getCreateUser();

                                  if (createUser != null) {
                                      noticeSummaryForList.setCreateUser(
                                              CreatedBySummary.builder()
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

    @Override
    public NoticeDetail getNotice(Long userId, Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.NOTICE_NOT_FOUND, "해당 공지사항을 찾을 수 없습니다.", noticeId));

        NoticeDetail noticeDetail = noticeConverter.toNoticeDetail(notice);

        // 주인인지 확인
        if (notice.getCreateUser().getUserId().equals(userId)) {
            noticeDetail.setOwner(true);
        }

        // 작성자 정보 매핑
        noticeDetail.setCreateUser(userConverter.toCreatedBySummary(notice.getCreateUser()));

        // 공지사항 아이디로 첨부파일리스트 조회 후 매핑
        noticeDetail.setAttachmentFiles(attachmentFileService.getAttachmentFilesSummaryByRefId(noticeId));

        return noticeDetail;
    }
}