package com.jetty.ssafficebe.schedule.payload;

import com.jetty.ssafficebe.common.code.ScheduleSourceType;
import com.jetty.ssafficebe.common.code.TaskType;
import com.jetty.ssafficebe.notice.entity.Notice;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleSummary {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private Long scheduleId;
    private String title;
    private String memo;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private TaskType taskType;
    private ScheduleSourceType scheduleSourceType;
    private String isEssentialYn;
    private String isFinishYn;
    private String isEnrollYn;

    private Notice notice;
    private List<RemindSummary> reminds;
}
