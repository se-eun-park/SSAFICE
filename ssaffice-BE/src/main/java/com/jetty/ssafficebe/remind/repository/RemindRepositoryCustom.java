package com.jetty.ssafficebe.remind.repository;

import java.time.LocalDateTime;

public interface RemindRepositoryCustom {

    boolean existsDuplicateRemind(Long scheduleId, String remindTypeCd, LocalDateTime remindDateTime);
}
