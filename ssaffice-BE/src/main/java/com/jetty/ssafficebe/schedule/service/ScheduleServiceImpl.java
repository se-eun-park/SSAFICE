package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.repository.NoticeRepository;
import com.jetty.ssafficebe.remind.converter.RemindConverter;
import com.jetty.ssafficebe.remind.payload.RemindRequest;
import com.jetty.ssafficebe.remind.repository.RemindRepository;
import com.jetty.ssafficebe.remind.service.RemindService;
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


    @Override
    public ApiResponse saveSchedule(Long userId, ScheduleRequest scheduleRequest) {
        // ! 1. Schedule 저장
        scheduleRequest.setUserId(userId);
        Schedule schedule = scheduleConverter.toSchedule(scheduleRequest);
        if (userId != null) {
            schedule.setUser(userRepository.findById(scheduleRequest.getUserId()).orElse(null));
        }
        if (scheduleRequest.getNoticeId() != null) {
            schedule.setNotice(noticeRepository.findById(scheduleRequest.getNoticeId()).orElse(null));
        }
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // 2. Remind 저장
        saveScheduleReminds(userId, scheduleRequest.getRemindRequests(), savedSchedule);

        // 3. Response 생성
        return createScheduleResponse(savedSchedule, "일정 등록에 성공하였습니다.");
    }


    @Override
    public ApiResponse updateSchedule(Long userId, Long scheduleId, ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                                              .orElseThrow(() -> new ResourceNotFoundException(
                                                      ErrorCode.SCHEDULE_NOT_FOUND,
                                                      "해당 일정을 찾을 수 없습니다.",
                                                      scheduleId));

        // 1. Schedule 정보 업데이트
        schedule.setTitle(scheduleRequest.getTitle());
        schedule.setMemo(scheduleRequest.getMemo());
        schedule.setStartDateTime(scheduleRequest.getStartDateTime());
        schedule.setEndDateTime(scheduleRequest.getEndDateTime());
        schedule.setScheduleSourceTypeCd(scheduleRequest.getScheduleSourceTypeCd());
        schedule.setScheduleStatusTypeCd(scheduleRequest.getScheduleStatusTypeCd());
        schedule.setIsEnrollYn("Y");

        // 2. 기존 알림 삭제 후 새로운 알림 추가
        schedule.getReminds().clear();
        remindRepository.deleteByScheduleId(scheduleId);
        saveScheduleReminds(userId, scheduleRequest.getRemindRequests(), schedule);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return createScheduleResponse(savedSchedule, "일정 수정에 성공하였습니다.");
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

    private void saveScheduleReminds(Long userId, List<RemindRequest> remindRequests, Schedule schedule) {
        if (remindRequests == null) return;

        remindRequests.forEach(remindRequest -> {
            remindRequest.setScheduleId(schedule.getScheduleId());
            try {
                remindService.saveRemind(userId, remindRequest);
            } catch (ResourceNotFoundException e) {
                if (e.getErrorCode() != ErrorCode.REMIND_ALREADY_EXISTS) {
                    throw e;
                }
            }
        });
    }

    private ApiResponse createScheduleResponse(Schedule schedule, String message) {
        ScheduleSummary scheduleSummary = scheduleConverter.toScheduleSummary(schedule);
        scheduleSummary.setRemindSummarys(schedule.getReminds().stream()
                                                  .map(remindConverter::toRemindSummary)
                                                  .collect(Collectors.toList()));

        return new ApiResponse(true, message, scheduleSummary);
    }
}