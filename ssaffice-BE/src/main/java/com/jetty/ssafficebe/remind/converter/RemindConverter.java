package com.jetty.ssafficebe.remind.converter;

import com.jetty.ssafficebe.remind.entity.Remind;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RemindConverter {

    Remind toRemind(LocalDateTime remindDateTime);
}
