package com.jetty.ssafficebe.schedule.payload;

import com.jetty.ssafficebe.schedule.entity.ScheduleCategory;
import com.jetty.ssafficebe.schedule.entity.ScheduleType;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {

    private String title;
    private String memo;
    private LocalTime remind;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isEssential;
    private ScheduleCategory category;
    private ScheduleType type;
    private String url;
}
