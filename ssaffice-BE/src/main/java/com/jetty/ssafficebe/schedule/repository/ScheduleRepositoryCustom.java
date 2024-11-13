package com.jetty.ssafficebe.schedule.repository;

import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepositoryCustom {

     Page<Schedule> findSchedulesByUserIdAndFilter(Long userId, ScheduleFilterRequest scheduleFilterRequest, Pageable pageable);

     Page<Schedule> findSchedulesByNoticeIdAndFilter(Long noticeId, ScheduleFilterRequest scheduleFilterRequest, Pageable pageable);

     Page<Schedule> findSchedulesByNoticeId(Long noticeId, Pageable pageable);
}
