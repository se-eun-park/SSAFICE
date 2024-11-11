package com.jetty.ssafficebe.channel.respository;

import com.jetty.ssafficebe.channel.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, String> {

}
