package com.jetty.ssafficebe.channel.controller;

import com.jetty.ssafficebe.channel.service.ChannelService;
import com.jetty.ssafficebe.user.payload.UserSummary;
import com.jetty.ssafficebe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;
    private final UserService userService;

    @GetMapping("/{channelId}/users")
    public ResponseEntity<Page<UserSummary>> getUserListByChannelId(@PathVariable String channelId,
        @PageableDefault(
            size = 20,
            sort = "userId",
            direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByChannelId(channelId, pageable));
    }

}
