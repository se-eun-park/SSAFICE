package com.jetty.ssafficebe.remind.repository;

import com.jetty.ssafficebe.remind.entity.Remind;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemindRepository extends JpaRepository<Remind, Long> {

    void deleteByScheduleId(Long scheduleId);

    boolean existsByScheduleIdAndRemindDateTime(Long scheduleId, LocalDateTime remindDateTime);
}
