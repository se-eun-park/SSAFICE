package com.jetty.ssafficebe.schedule.converter;

import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ScheduleConverter {
    Schedule toSchedule(ScheduleRequest scheduleRequest);

    ScheduleResponse toScheduleResponse(Schedule schedule);
}
