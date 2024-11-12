package com.jetty.ssafficebe.schedule.repository;

import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepositoryCustom {

     Page<Schedule> getSchedulesByFilter(ScheduleFilterRequest scheduleFilterRequest, Pageable pageable);

     Page<Schedule> getSchedulesByNoticeId(Long noticeId, Pageable pageable);
}
