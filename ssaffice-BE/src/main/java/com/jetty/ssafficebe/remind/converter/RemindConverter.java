package com.jetty.ssafficebe.remind.converter;

import com.jetty.ssafficebe.remind.entity.Remind;
import com.jetty.ssafficebe.remind.payload.RemindSummary;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RemindConverter {

    Remind toRemind(LocalDateTime remindDateTime, Long scheduleId);

    default List<Remind> toRemindList(LocalDateTime startDateTime, LocalDateTime endDateTime, Long scheduleId) {
        List<Remind> reminds = new ArrayList<>();
        LocalDateTime currentDate = startDateTime;

        while (!currentDate.isAfter(endDateTime)) {
            reminds.add(toRemind(currentDate, scheduleId));
            currentDate = currentDate.plusDays(1);
        }

        return reminds;
    }

    RemindSummary toRemindSummary(Remind remind);
}
