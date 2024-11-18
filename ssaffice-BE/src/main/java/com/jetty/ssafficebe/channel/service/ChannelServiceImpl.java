package com.jetty.ssafficebe.channel.service;

import com.jetty.ssafficebe.channel.converter.ChannelConverter;
import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.channel.entity.UserChannel;
import com.jetty.ssafficebe.channel.payload.ChannelSummary;
import com.jetty.ssafficebe.channel.respository.UserChannelRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelConverter channelConverter;

    private final UserChannelRepository userChannelRepository;


    @Override
    public List<ChannelSummary> getChannelsByUserId(Long userId) {
        List<UserChannel> userChannels = userChannelRepository.findDistinctByUserId(userId);

        List<Channel> channels = userChannels.stream()
                                             .map(UserChannel::getChannel)
                                             .toList();

        return channelConverter.toChannelSummaryList(channels);
    }

    public List<String> getChannelIdsByUserId(Long userId) {
        return userChannelRepository.findChannelIdsByUserId(userId);
    }
}
