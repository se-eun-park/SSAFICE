package com.jetty.ssafficebe.schedule.converter;

import com.jetty.ssafficebe.schedule.entity.Remind;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RemindConverter {

    Remind toRemind(LocalDateTime remindDateTimes);

    default List<Remind> toRemindList(List<LocalDateTime> remindDateTimes) {
        return remindDateTimes.stream()
                              .map(this::toRemind)
                              .collect(Collectors.toList());
    }
}
