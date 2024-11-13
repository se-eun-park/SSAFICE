package com.jetty.ssafficebe.channel.service;

import com.jetty.ssafficebe.channel.converter.ChannelConverter;
import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.channel.payload.ChannelSummary;
import com.jetty.ssafficebe.channel.respository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelConverter channelConverter;

    @Override
    public Page<ChannelSummary> getChannelsByUserId(Long userId, Pageable pageable) {
        Page<Channel> channelsPageByUserId = channelRepository.findChannelsByUserId(userId, pageable);
		return channelsPageByUserId.map(channelConverter::toChannelSummary);
    }
}
