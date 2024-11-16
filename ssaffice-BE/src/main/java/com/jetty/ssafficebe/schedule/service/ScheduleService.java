package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.schedule.payload.ScheduleDetail;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import com.jetty.ssafficebe.schedule.payload.SchedulePageResponse;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummary;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    ApiResponse saveSchedule(Long userId, ScheduleRequest scheduleRequest);

    ApiResponse saveSchedulesByAdmin(List<Long> userIds, ScheduleRequest scheduleRequest);

    void saveSchedulesFromNotice(Notice notice, List<Long> userIds);

    ApiResponse updateSchedule(Long userId, Long scheduleId, ScheduleRequest scheduleRequest);

    ScheduleDetail getSchedule(Long userId, Long scheduleId);

    ApiResponse deleteSchedule(Long userId, Long scheduleId);

    SchedulePageResponse getSchedules(Long userId, ScheduleFilterRequest scheduleFilterRequest, Pageable pageable);

    SchedulePageResponse getSchedulesByNoticeForAdmin(Long noticeId, ScheduleFilterRequest scheduleFilterRequest, Pageable pageable);

    Page<ScheduleSummary> getUnregisteredNoticeSchedules(Long userId, ScheduleFilterRequest scheduleFilterRequest, Pageable pageable);

    SchedulePageResponse getAssignedSchedules(Long userId, ScheduleFilterRequest scheduleFilterRequest, Pageable pageable);
}
