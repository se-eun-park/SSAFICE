package com.jetty.ssafficebe.search.esnotice.converter;

import com.jetty.ssafficebe.search.esnotice.document.ESNotice;
import com.jetty.ssafficebe.search.esnotice.payload.ESNoticeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ESNoticeConverter {

    ESNotice toESNotice(ESNoticeRequest request);

}
