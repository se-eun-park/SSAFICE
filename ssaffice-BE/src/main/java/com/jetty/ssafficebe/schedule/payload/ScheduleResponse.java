package com.jetty.ssafficebe.schedule.payload;

import com.jetty.ssafficebe.schedule.entity.TaskType;
import com.jetty.ssafficebe.schedule.entity.ScheduleSourceType;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResponse {

    private Long scheduleId;
    private String title;
    private String memo;
    private LocalTime remind;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isEssential;
    private TaskType category;
    private ScheduleSourceType type;
    private Boolean isFinish;
    private Boolean isEnroll;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    private Notice notice;
}
