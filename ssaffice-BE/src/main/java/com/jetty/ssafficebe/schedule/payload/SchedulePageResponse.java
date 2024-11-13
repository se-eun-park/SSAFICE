package com.jetty.ssafficebe.schedule.payload;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class SchedulePageResponse {

    private Page<ScheduleSummary> scheduleSummaryPage;
    private List<Long> statusCounts;
}
