package com.jetty.ssafficebe.channel.controller;

import com.jetty.ssafficebe.channel.service.ChannelService;
import com.jetty.ssafficebe.user.payload.UserSummary;
import com.jetty.ssafficebe.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<UserSummary>> getUserListByChannelId(@PathVariable String channelId) {
        return ResponseEntity.ok(userService.getUsersByChannelId(channelId));
    }

}
