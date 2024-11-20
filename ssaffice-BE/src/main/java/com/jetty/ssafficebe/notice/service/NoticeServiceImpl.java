package com.jetty.ssafficebe.notice.service;

import com.jetty.ssafficebe.channel.payload.ChannelSummary;
import com.jetty.ssafficebe.channel.service.ChannelService;
import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidAuthorizationException;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.file.service.AttachmentFileService;
import com.jetty.ssafficebe.notice.converter.NoticeConverter;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.payload.NoticeDetail;
import com.jetty.ssafficebe.notice.payload.NoticeFilterRequest;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.payload.NoticeSummary;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForAdmin;
import com.jetty.ssafficebe.notice.repository.NoticeRepository;
import com.jetty.ssafficebe.schedule.service.ScheduleService;
import com.jetty.ssafficebe.search.service.ESNoticeService;
import com.jetty.ssafficebe.user.converter.UserConverter;
import com.jetty.ssafficebe.user.repository.UserRepository;
import com.jetty.ssafficebe.user.service.UserService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final ChannelService channelService;

    private final UserConverter userConverter;
    private final UserService userService;
    private final UserRepository userRepository;

    private final ESNoticeService esNoticeService;

    @Override
    public ApiResponse saveNotice(NoticeRequest noticeRequest, List<MultipartFile> files) throws IOException {
        Notice notice = noticeConverter.toNotice(noticeRequest);
        Notice savedNotice = noticeRepository.save(notice);

        // 파일 업로드
        for (MultipartFile file : files) {
            attachmentFileService.uploadFile(file, "notice", savedNotice.getNoticeId());
        }

        // 1. 채널 아이디로 공지 대상자 조회
        List<Long> usersByChannelId = userService.getUserIdsByChannelId(noticeRequest.getChannelId());

        // 2. 공지 대상자 일정 추가
        scheduleService.saveSchedulesFromNotice(savedNotice, usersByChannelId);

        // 3. ElasticSearch에 공지 추가
        esNoticeService.saveNotice(savedNotice);

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

        // ElasticSearch에서도 삭제
        esNoticeService.deleteNotice(noticeId);

        return new ApiResponse(true, HttpStatus.OK, "공지사항 삭제 성공", noticeId);
    }

    @Override
    public Page<NoticeSummary> getNoticePage(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND, "해당 유저를 찾을 수 없습니다.", userId);
        }

        // 1. 유저가 속해있는 채널 아이디 조회
        List<ChannelSummary> channelsByUserId = channelService.getChannelsByUserId(userId);
        List<String> channelIds = channelsByUserId.stream()
                                                  .map(ChannelSummary::getChannelId)
                                                  .toList();

        if (channelIds.isEmpty()) {
            return Page.empty();
        }

        // 2. 채널 아이디로 공지사항 조회
        Page<Notice> noticePage = noticeRepository.findByChannelIdIn(channelIds, pageable);

        return noticePage.map(noticeConverter::toNoticeSummary);
    }

    @Override
    public NoticeDetail getNotice(Long userId, Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.NOTICE_NOT_FOUND, "해당 공지사항을 찾을 수 없습니다.", noticeId));

        System.out.println("noticeBoolean : " + notice.isEssential());

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

    @Override
    public List<NoticeSummaryForAdmin> getNoticePageByCreateUser(Long userId, NoticeFilterRequest noticeFilterRequest,
                                                                 Sort sort) {

        List<Notice> result = noticeRepository.getNoticeListByCreateUserAndFilter(userId, noticeFilterRequest, sort);
        List<NoticeSummary> noticeSummaryList = noticeConverter.toNoticeSummaryList(result);

        // 수행여부 count 추가
        return noticeSummaryList.stream().map(noticeSummary -> {
            NoticeSummaryForAdmin noticeSummaryForAdmin = new NoticeSummaryForAdmin();
            noticeSummaryForAdmin.setNoticeSummary(noticeSummary);
            noticeSummaryForAdmin.setScheduleEnrolledCount(scheduleService.getEnrolledCount(noticeSummary.getNoticeId()));
            return noticeSummaryForAdmin;
        }).toList();
    }
}