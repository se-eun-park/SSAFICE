package com.jetty.ssafficebe.channel.converter;

import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.channel.payload.ChannelSummary;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ChannelConverter {

    @Mapping(source = "mmTeam.mmTeamName", target = "mmTeamName")
    ChannelSummary toChannelSummary(Channel channel);

    List<ChannelSummary> toChannelSummaryList(List<Channel> channelList);
}
