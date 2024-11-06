package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;

public interface ScheduleService {

    ApiResponse saveSchedule(ScheduleRequest scheduleRequest);

    ApiResponse updateSchedule(String scheduleId, ScheduleRequest scheduleRequest);
}
