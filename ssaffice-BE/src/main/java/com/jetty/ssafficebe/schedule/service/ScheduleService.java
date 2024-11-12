package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.schedule.payload.ScheduleDetail;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {

    ApiResponse saveSchedule(Long userId, ScheduleRequest scheduleRequest);

    ApiResponse updateSchedule(Long userId, Long scheduleId, ScheduleRequest scheduleRequest);

    ScheduleDetail getSchedule(Long userId, Long scheduleId);

    ApiResponse deleteSchedule(Long userId, Long scheduleId);

    Page<ScheduleSummary> getScheduleList(ScheduleFilterRequest scheduleFilterRequest, Pageable pageable);
}
