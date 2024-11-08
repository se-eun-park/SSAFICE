package com.jetty.ssafficebe.schedule.converter;

import com.jetty.ssafficebe.remind.converter.RemindConverter;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN, uses = {
        RemindConverter.class})
public interface ScheduleConverter {
    @Mapping(target = "scheduleStatusTypeCd", constant = "TODO")
    Schedule toSchedule(ScheduleRequest scheduleRequest);

    @Mapping(target = "remindSummarys", source = "reminds")
    ScheduleSummary toScheduleSummary(Schedule schedule);
}