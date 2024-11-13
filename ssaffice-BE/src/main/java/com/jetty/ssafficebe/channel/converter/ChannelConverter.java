package com.jetty.ssafficebe.channel.converter;

import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.channel.payload.ChannelSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChannelConverter {

	@Mapping(source = "mmTeam.mmTeamId", target = "mmTeamId")
	@Mapping(source = "mmTeam.mmTeamName", target = "mmTeamName")
	ChannelSummary toChannelSummary(Channel channel);



}
