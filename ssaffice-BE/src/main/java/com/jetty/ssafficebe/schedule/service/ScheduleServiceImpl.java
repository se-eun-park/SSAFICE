package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.DuplicateValueException;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidAuthorizationException;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.repository.NoticeRepository;
import com.jetty.ssafficebe.remind.converter.RemindConverter;
import com.jetty.ssafficebe.remind.payload.RemindRequest;
import com.jetty.ssafficebe.remind.repository.RemindRepository;
import com.jetty.ssafficebe.remind.service.RemindService;
import com.jetty.ssafficebe.role.repository.UserRoleRepository;
import com.jetty.ssafficebe.schedule.converter.ScheduleConverter;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummary;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummaryForList;
import com.jetty.ssafficebe.schedule.repository.ScheduleRepository;
import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.payload.CreatedBySummary;
import com.jetty.ssafficebe.user.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleConverter scheduleConverter;
    private final ScheduleRepository scheduleRepository;
    private final RemindConverter remindConverter;
    private final RemindRepository remindRepository;
    private final RemindService remindService;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public ApiResponse saveSchedule(Long userId, ScheduleRequest scheduleRequest) {
        // ! 1. 권한 검증: 요청한 사용자가 일정 소유자이거나 관리자인 경우만 허용
        validateAuthorization(userId, scheduleRequest.getUserId(), "create");

        // ! 2. Schedule 엔티티 생성 및 연관관계 설정
        Schedule schedule = scheduleConverter.toSchedule(scheduleRequest);

        // 일정 소유자 설정
        schedule.setUser(userRepository.findById(scheduleRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.USER_NOT_FOUND, "해당 사용자를 찾을 수 없습니다.", scheduleRequest.getUserId())));

        if (scheduleRequest.getNoticeId() != null) {
            schedule.setNotice(noticeRepository.findById(scheduleRequest.getNoticeId()).orElseThrow(() -> new ResourceNotFoundException(
                    ErrorCode.NOTICE_NOT_FOUND, "해당 공지사항을 찾을 수 없습니다.", scheduleRequest.getNoticeId())));
        }

        // ! 3. Schedule 저장
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // ! 4. Remind 엔티티 생성 및 연관관계 설정
        saveScheduleReminds(userId, scheduleRequest.getRemindRequests(), savedSchedule);

        return new ApiResponse(true, "일정 등록에 성공하였습니다.", createScheduleSummaryWithReminds(savedSchedule));
    }


    @Override
    public ApiResponse updateSchedule(Long userId, Long scheduleId, ScheduleRequest scheduleRequest) {
        // ! 1. 수정할 Schedule 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.SCHEDULE_NOT_FOUND, "해당 일정을 찾을 수 없습니다.", scheduleId));

        // ! 2. 권한 검증: 요청한 사용자가 일정 소유자이거나 관리자인 경우만 허용
        validateAuthorization(userId, scheduleRequest.getUserId(), "update");


        // ! 3. Schedule 기본 정보 업데이트
        schedule.setTitle(scheduleRequest.getTitle());
        schedule.setMemo(scheduleRequest.getMemo());
        schedule.setStartDateTime(scheduleRequest.getStartDateTime());
        schedule.setEndDateTime(scheduleRequest.getEndDateTime());
        schedule.setScheduleSourceTypeCd(scheduleRequest.getScheduleSourceTypeCd());
        schedule.setScheduleStatusTypeCd(scheduleRequest.getScheduleStatusTypeCd());
        schedule.setIsEnrollYn("Y");

        // ! 4. Remind 정보 갱신
        remindRepository.deleteByScheduleId(scheduleId);
        schedule.getReminds().clear();
        saveScheduleReminds(userId, scheduleRequest.getRemindRequests(), schedule);

        // ! 5. Schedule 저장 및 Response 반환
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ApiResponse(true, "일정 수정에 성공하였습니다.", createScheduleSummaryWithReminds(savedSchedule));
    }

    @Override
    public ApiResponse getSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                                              .orElseThrow(() -> new ResourceNotFoundException(
                                                      ErrorCode.SCHEDULE_NOT_FOUND,
                                                      "해당 일정을 찾을 수 없습니다.",
                                                      scheduleId));
        return new ApiResponse(true, "일정 조회에 성공하였습니다.", scheduleConverter.toScheduleSummary(schedule));
    }

    @Override
    public ApiResponse deleteSchedule(Long scheduleId) {
        scheduleRepository.delete(
                scheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.SCHEDULE_NOT_FOUND, "해당 일정을 찾을 수 없습니다.", scheduleId)));
        return new ApiResponse(true, "일정 조회에 성공하였습니다.", scheduleId);
    }

    @Override
    public Page<ScheduleSummaryForList> getScheduleList(ScheduleFilterRequest filterRequest, Pageable pageable) {
        Page<Schedule> schedulesPage = scheduleRepository.getSchedulesByFilter(filterRequest, pageable);

        return schedulesPage.map(schedule -> {
            ScheduleSummaryForList summary = scheduleConverter.toScheduleSummaryForList(schedule);

            if (schedule.getNotice() != null && schedule.getNotice().getCreateUser() != null) {
                User createUser = schedule.getNotice().getCreateUser();
                summary.setCreateUser(CreatedBySummary.builder()
                                                      .userId(createUser.getUserId())
                                                      .name(createUser.getName())
                                                      .email(createUser.getEmail())
                                                      .profileImgUrl(createUser.getProfileImgUrl())
                                                      .build());
            }

            return summary;
        });
    }

    private void validateAuthorization(Long userId, Long requestUserId, String type) {
        boolean isAdmin = userRoleRepository.existsByUserIdAndRoleIdIn(userId, Arrays.asList("ROLE_ADMIN", "ROLE_SYSADMIN"));

        if (!requestUserId.equals(userId) && !isAdmin) {
            ErrorCode errorCode = type.equals("create") ? ErrorCode.SCHEDULE_CREATE_FORBIDDEN : ErrorCode.SCHEDULE_UPDATE_FORBIDDEN;
            String errorMessage = type.equals("create") ? "일정 등록 권한이 없습니다." : "일정 수정 권한이 없습니다.";
            throw new InvalidAuthorizationException(errorCode, errorMessage, userId);
        }
    }

    private void saveScheduleReminds(Long userId, List<RemindRequest> remindRequests, Schedule schedule) {
        if (remindRequests == null) return;

        remindRequests.forEach(remindRequest -> {
            remindRequest.setScheduleId(schedule.getScheduleId());
            try {
                remindService.saveRemind(userId, remindRequest);
            } catch (DuplicateValueException e) {
                if (e.getErrorCode() != ErrorCode.REMIND_ALREADY_EXISTS) {
                    throw e;
                }
            }
        });
    }

    private ScheduleSummary createScheduleSummaryWithReminds(Schedule schedule) {
        ScheduleSummary summary = scheduleConverter.toScheduleSummary(schedule);
        summary.setRemindSummarys(schedule.getReminds().stream()
                                          .map(remindConverter::toRemindSummary)
                                          .collect(Collectors.toList()));
        return summary;
    }
}