package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.converter.NoticeConverter;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.repository.NoticeRepository;
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
    private final NoticeRepository noticeRepository;
    private final NoticeConverter noticeConverter;

    @Override
    @Transactional
    public ApiResponse saveSchedule(ScheduleRequest scheduleRequest) {
        // ! 1. Schedule 저장
        Schedule schedule = scheduleConverter.toSchedule(scheduleRequest);

        // Notice 설정 (필수 공지 파생의 경우)
        if (scheduleRequest.getNoticeId() != null) {
            Notice notice = noticeRepository.findById(scheduleRequest.getNoticeId())
                                            .orElseThrow(() -> new ResourceNotFoundException(
                                                    ErrorCode.NOTICE_NOT_FOUND,
                                                    "해당 공지사항을 찾을 수 없습니다.",
                                                    scheduleRequest.getNoticeId()));
            schedule.setNotice(notice);
            schedule.setIsEssentialYn("Y");
        }

        Schedule savedSchedule = scheduleRepository.save(schedule);

        // ! 2. Remind 생성 및 저장 -> TODO : remind api 쪽으로 분리할 예정
        scheduleRequest.getRemindRequestList().forEach(remindRequest -> {
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

        // Notice 설정 (필수 공지 파생의 경우)
        if (scheduleRequest.getNoticeId() != null) {
            scheduleSummary.setNoticeSummaryForList(
                    noticeConverter.toNoticeSummaryForList(schedule.getNotice()));
        }

        scheduleSummary.setRemindSummaryList(savedSchedule.getRemindList().stream()
                                                          .map(remindConverter::toRemindSummary)
                                                          .collect(Collectors.toList()));

        return new ApiResponse(true, "일정 등록에 성공하였습니다.", scheduleSummary);
    }

    @Override
    public ApiResponse updateSchedule(String scheduleId, ScheduleRequest scheduleRequest) {
        return new ApiResponse(true, "일정 수정에 성공하였습니다.");
    }

}
