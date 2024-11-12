package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.schedule.payload.ScheduleDetail;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import com.jetty.ssafficebe.schedule.payload.SchedulePageResponse;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    ApiResponse saveSchedule(Long userId, ScheduleRequest scheduleRequest);

    ApiResponse updateSchedule(Long userId, Long scheduleId, ScheduleRequest scheduleRequest);

    ScheduleDetail getSchedule(Long userId, Long scheduleId);

    ApiResponse deleteSchedule(Long userId, Long scheduleId);

    SchedulePageResponse getScheduleList(Long userId, ScheduleFilterRequest scheduleFilterRequest, Pageable pageable);

    void saveSchedulesForUsers(Long noticeId, List<Long> userIds);

    SchedulePageResponse getSchedulesByNoticeForAdmin(Long noticeId, ScheduleFilterRequest scheduleFilterRequest, Pageable pageable);

    SchedulePageResponse getSchedulesByNoticeForAdmin(Long noticeId, Pageable pageable);
}
