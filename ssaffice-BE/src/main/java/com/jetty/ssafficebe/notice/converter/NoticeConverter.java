package com.jetty.ssafficebe.notice.converter;

import com.jetty.ssafficebe.channel.converter.ChannelConverter;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.payload.NoticeDetail;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForList;
import com.jetty.ssafficebe.user.converter.UserConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {ChannelConverter.class, UserConverter.class},
        unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface NoticeConverter {

    Notice toNotice(NoticeRequest noticeRequest);

    @Mapping(source = "channel", target = "channelSummary")
    @Mapping(source = "createUser", target = "createUser")
    NoticeSummaryForList toNoticeSummaryForList(Notice notice);

    NoticeDetail toNoticeDetail(Notice notice);
}
