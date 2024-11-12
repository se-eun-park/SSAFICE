package com.jetty.ssafficebe.schedule.converter;

import com.jetty.ssafficebe.remind.converter.RemindConverter;
import com.jetty.ssafficebe.notice.converter.NoticeConverter;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.SchedulePageResponse;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleDetail;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummary;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN, uses = {
        RemindConverter.class, NoticeConverter.class})
public interface ScheduleConverter {

    Schedule toSchedule(Long userId, Long noticeId);

    Schedule toSchedule(ScheduleRequest scheduleRequest);

    @Mapping(target = "remindSummarys", source = "reminds")
    @Mapping(target = "noticeSummaryForList", source = "notice")
    @Mapping(target = "createUser", source = "user")
    ScheduleDetail toScheduleDetail(Schedule schedule);

    ScheduleSummary toScheduleSummary(Schedule schedule);

    SchedulePageResponse toSchedulePageResponse(Page<ScheduleSummary> scheduleSummaryPage, List<Integer> statusCounts);
}