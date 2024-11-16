package com.jetty.ssafficebe.channel.service;


import com.jetty.ssafficebe.channel.payload.ChannelSummary;
import java.util.List;

public interface ChannelService {

    List<ChannelSummary> getChannelsByUserId(Long userId);
}
