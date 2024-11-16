package com.jetty.ssafficebe.schedule.payload;

import com.jetty.ssafficebe.remind.payload.RemindRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {

    private String title;
    private String memo;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String scheduleSourceTypeCd;
    private String scheduleStatusTypeCd;
    private String isEssentialYn;
    private String isEnrollYn;
    private Long userId;
    private Long noticeId;
    private List<RemindRequest> remindRequests;
}
