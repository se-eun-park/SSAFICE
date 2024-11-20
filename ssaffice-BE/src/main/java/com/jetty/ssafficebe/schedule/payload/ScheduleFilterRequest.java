package com.jetty.ssafficebe.schedule.payload;

import com.jetty.ssafficebe.common.payload.BaseFilterRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleFilterRequest extends BaseFilterRequest {

    private String enrollYn;
    private String scheduleSourceTypeCd;
}
