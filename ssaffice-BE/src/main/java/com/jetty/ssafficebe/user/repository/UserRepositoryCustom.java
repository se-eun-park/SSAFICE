package com.jetty.ssafficebe.user.repository;

import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.payload.UserFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<User> findUsersByRoleId(String roleId, Pageable pageable);

    Page<User> getUsersByFilter(UserFilterRequest userFilterRequest, Pageable pageable);

    Page<User> findUsersByChannelId(String channelId, Pageable pageable);
}
