package com.jetty.ssafficebe.schedule.converter;

import com.jetty.ssafficebe.schedule.entity.Remind;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RemindConverter {

    Remind toRemind(LocalDateTime remindDateTime);
}
