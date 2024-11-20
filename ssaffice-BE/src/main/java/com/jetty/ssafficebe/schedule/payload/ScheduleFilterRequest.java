package com.jetty.ssafficebe.schedule.payload;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleFilterRequest {

    private String filterType;
    private String enrollYn;
    private String scheduleSourceTypeCd;
    private LocalDateTime filterStartDateTime;
    private LocalDateTime filterEndDateTime;
}
