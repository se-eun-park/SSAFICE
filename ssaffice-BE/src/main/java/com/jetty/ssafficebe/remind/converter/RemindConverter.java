package com.jetty.ssafficebe.remind.converter;

import com.jetty.ssafficebe.remind.entity.Remind;
import com.jetty.ssafficebe.remind.payload.RemindDetail;
import com.jetty.ssafficebe.remind.payload.RemindRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface RemindConverter {

    Remind toRemind(RemindRequest remindRequest);

    RemindDetail toRemindSummary(Remind remind);
}
