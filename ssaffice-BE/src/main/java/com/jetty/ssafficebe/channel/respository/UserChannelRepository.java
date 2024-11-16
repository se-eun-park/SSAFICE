package com.jetty.ssafficebe.channel.respository;

import com.jetty.ssafficebe.channel.entity.UserChannel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChannelRepository extends JpaRepository<UserChannel, Long>, ChannelRepositoryCustom {

    List<UserChannel> findAllByUserId(Long userId);
}
