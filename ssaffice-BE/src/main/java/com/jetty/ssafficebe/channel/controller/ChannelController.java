package com.jetty.ssafficebe.channel.controller;

import com.jetty.ssafficebe.channel.payload.ChannelSummary;
import com.jetty.ssafficebe.channel.service.ChannelService;
import com.jetty.ssafficebe.common.security.userdetails.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ChannelSummary>> getChannelListByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(channelService.getChannelsByUserId(userId));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ChannelSummary>> getChannelList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(channelService.getChannelsByUserId(userDetails.getUserId()));
    }

    @GetMapping("/ids/{userId}")
    public ResponseEntity<List<String>> getChannelIdsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(channelService.getChannelIdsByUserId(userId));
    }

}