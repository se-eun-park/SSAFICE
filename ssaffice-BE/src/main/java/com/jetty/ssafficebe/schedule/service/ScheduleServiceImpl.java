package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.repository.NoticeRepository;
import com.jetty.ssafficebe.schedule.converter.RemindConverter;
import com.jetty.ssafficebe.schedule.converter.ScheduleConverter;
import com.jetty.ssafficebe.schedule.entity.Remind;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.repository.RemindRepository;
import com.jetty.ssafficebe.schedule.repository.ScheduleRepository;
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

    @Override
    @Transactional
    public ApiResponse saveSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleConverter.toSchedule(scheduleRequest);
        schedule.setNotice(noticeRepository.findById(scheduleRequest.getNoticeId())
                                           .orElseThrow(() -> new ResourceNotFoundException(
                                                   ErrorCode.NOTICE_NOT_FOUND, "해당 공지사항을 찾을 수 없습니다.",
                                                   scheduleRequest.getNoticeId())));

        Schedule savedSchedule = scheduleRepository.save(schedule);

        scheduleRequest.getRemindDateTimes().forEach(dateTime -> {
            Remind remind = remindConverter.toRemind(dateTime);
            remind.setScheduleId(savedSchedule.getScheduleId());
            savedSchedule.addRemind(remind);
            remindRepository.save(remind);
        });

        return new ApiResponse(true, "일정 등록에 성공하였습니다.", scheduleConverter.toScheduleSummary(savedSchedule));
    }
}
