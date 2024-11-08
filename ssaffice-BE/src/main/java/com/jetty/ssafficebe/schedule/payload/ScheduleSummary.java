package com.jetty.ssafficebe.schedule.payload;

import com.jetty.ssafficebe.notice.payload.NoticeSummaryForList;
import com.jetty.ssafficebe.remind.payload.RemindSummary;
import com.jetty.ssafficebe.user.payload.CreatedBySummary;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleSummary {

    private Long scheduleId;
    private String title;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String scheduleSourceTypeCd;
    private String scheduleStatusTypeCd;
    private String isEssentialYn;
    private String isEnrollYn;
    private CreatedBySummary createUser;

    private NoticeSummaryForList noticeSummaryForList;
    private List<RemindSummary> remindSummaryList;
}
