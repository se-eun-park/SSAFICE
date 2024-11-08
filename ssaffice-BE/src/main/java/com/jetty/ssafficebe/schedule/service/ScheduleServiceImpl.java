package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.remind.converter.RemindConverter;
import com.jetty.ssafficebe.remind.entity.Remind;
import com.jetty.ssafficebe.remind.repository.RemindRepository;
import com.jetty.ssafficebe.schedule.converter.ScheduleConverter;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummary;
import com.jetty.ssafficebe.schedule.repository.ScheduleRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleConverter scheduleConverter;
    private final ScheduleRepository scheduleRepository;
    private final RemindConverter remindConverter;
    private final RemindRepository remindRepository;

    @Override
    @Transactional
    public ApiResponse saveSchedule(ScheduleRequest scheduleRequest) {
        // ! 1. Schedule 저장
        Schedule schedule = scheduleConverter.toSchedule(scheduleRequest);
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
        schedule.setScheduleStatusTypeCd(scheduleRequest.getScheduleStatusTypeCd());
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

}
