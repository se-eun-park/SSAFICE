package com.jetty.ssafficebe.search.converter;

import com.jetty.ssafficebe.search.document.ESNotice;
import com.jetty.ssafficebe.search.payload.ESNoticeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ESNoticeConverter {

    ESNotice toESNotice(ESNoticeRequest request);

}
