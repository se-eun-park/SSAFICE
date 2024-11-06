package com.jetty.ssafficebe.schedule.converter;

import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummary;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ScheduleConverter {

    Schedule toSchedule(ScheduleRequest scheduleRequest);

    ScheduleSummary toScheduleSummary(Schedule schedule);
}