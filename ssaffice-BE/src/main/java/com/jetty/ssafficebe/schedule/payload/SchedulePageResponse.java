package com.jetty.ssafficebe.schedule.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
public class SchedulePageResponse {

    private Page<ScheduleSummary> scheduleSummaryPage;
    private ScheduleEnrolledCount scheduleEnrolledCount;
    private ScheduleStatusCount scheduleStatusCount;
}
