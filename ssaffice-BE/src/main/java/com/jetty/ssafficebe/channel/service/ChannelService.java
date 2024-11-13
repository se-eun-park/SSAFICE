package com.jetty.ssafficebe.channel.service;

import com.jetty.ssafficebe.channel.payload.ChannelSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChannelService {

	Page<ChannelSummary> getChannelsByUserId(Long userId, Pageable pageable);
}
