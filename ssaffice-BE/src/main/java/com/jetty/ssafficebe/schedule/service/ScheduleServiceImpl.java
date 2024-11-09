package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.repository.NoticeRepository;
import com.jetty.ssafficebe.remind.converter.RemindConverter;
import com.jetty.ssafficebe.remind.entity.Remind;
import com.jetty.ssafficebe.remind.repository.RemindRepository;
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
import java.util.Collections;
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
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;


    @Override
    public ApiResponse saveSchedule(ScheduleRequest scheduleRequest) {
        // ! 1. Schedule 저장
        Schedule schedule = scheduleConverter.toSchedule(scheduleRequest);
        if (scheduleRequest.getUserId() != null) {
            schedule.setUser(userRepository.findById(scheduleRequest.getUserId()).orElse(null));
        }
        if (scheduleRequest.getNoticeId() != null) {
            schedule.setNotice(noticeRepository.findById(scheduleRequest.getNoticeId()).orElse(null));
        }
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // ! 2. Remind 생성 및 저장 -> TODO : remind api 쪽으로 분리할 예정
        scheduleRequest.getRemindRequests().forEach(remindRequest -> {
            List<Remind> reminds = "DAILY".equals(remindRequest.getType()) ?
                                   remindConverter.toRemindList(remindRequest.getRemindDateTime(),
                                           schedule.getEndDateTime(),
                                           savedSchedule.getScheduleId()) :
                                   Collections.singletonList(remindConverter.toRemind(
                                           remindRequest.getRemindDateTime(),
                                           savedSchedule.getScheduleId()));

            reminds.forEach(remind -> {
                savedSchedule.addRemind(remind);
                remindRepository.save(remind);
            });
        });

        // ! 3. Response 생성
        ScheduleSummary scheduleSummary = scheduleConverter.toScheduleSummary(savedSchedule);

        scheduleSummary.setRemindSummarys(savedSchedule.getReminds().stream()
                                                       .map(remindConverter::toRemindSummary)
                                                       .collect(Collectors.toList()));

        return new ApiResponse(true, "일정 등록에 성공하였습니다.", scheduleSummary);
    }


    // TODO : 종료 시간 변경 시 리마인드 삭제 파트 추가
    @Override
    public ApiResponse updateSchedule(Long scheduleId, ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                                              .orElseThrow(() -> new ResourceNotFoundException(
                                                      ErrorCode.SCHEDULE_NOT_FOUND,
                                                      "해당 일정을 찾을 수 없습니다.",
                                                      scheduleId));

        schedule.setTitle(scheduleRequest.getTitle());
        schedule.setMemo(scheduleRequest.getMemo());
        schedule.setStartDateTime(scheduleRequest.getStartDateTime());
        schedule.setEndDateTime(scheduleRequest.getEndDateTime());
        schedule.setScheduleSourceTypeCd(scheduleRequest.getScheduleSourceTypeCd());
        schedule.setScheduleStatusTypeCd(scheduleRequest.getScheduleStatusTypeCd());
        schedule.setIsEnrollYn("Y");

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ApiResponse(true, "일정 수정에 성공하였습니다.", scheduleConverter.toScheduleSummary(savedSchedule));
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
}