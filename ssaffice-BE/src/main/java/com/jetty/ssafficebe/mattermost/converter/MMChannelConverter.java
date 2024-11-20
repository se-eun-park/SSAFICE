package com.jetty.ssafficebe.mattermost.converter;

import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.mattermost.payload.MMChannelSummary;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MMChannelConverter {

    @Mapping(source = "id", target = "channelId")
    @Mapping(source = "displayName", target = "channelName")
    @Mapping(source = "teamId", target = "mmTeamId")
    List<Channel> toChannelList(List<MMChannelSummary> mmChannelSummaryList);

}
