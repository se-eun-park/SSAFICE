package com.jetty.ssafficebe.schedule.repository;

import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepositoryCustom {

    Page<Schedule> findSchedulesByUserIdAndFilter(Long userId, ScheduleFilterRequest filterRequest, Pageable pageable);

    Page<Schedule> findSchedulesByNoticeIdAndFilter(Long noticeId, ScheduleFilterRequest filterRequest, Pageable pageable);

    List<Long> getStatusCounts(List<Schedule> schedules);

    List<Long> getCompletionCounts(List<Schedule> schedules);
}
