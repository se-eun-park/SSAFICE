package com.jetty.ssafficebe.user.payload;

import com.jetty.ssafficebe.notice.payload.NoticeCounts;
import com.jetty.ssafficebe.schedule.payload.ScheduleStatusCount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashBoardCount {

    NoticeCounts noticeCounts;
    ScheduleStatusCount scheduleCounts;
}
