package com.jetty.ssafficebe.mattermost.controller;

import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.mattermost.payload.MMChannelSummary;
import com.jetty.ssafficebe.mattermost.payload.PostRequest;
import com.jetty.ssafficebe.mattermost.payload.PostSummary;
import com.jetty.ssafficebe.mattermost.payload.PostUpdateRequest;
import com.jetty.ssafficebe.mattermost.payload.UserAutocompleteSummary;
import com.jetty.ssafficebe.mattermost.service.MattermostService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mm")
public class MattermostController {

    private final MattermostService mattermostService;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostSummary> getPost(@PathVariable String postId) {
        return ResponseEntity.ok(this.mattermostService.getPost(postId));
    }

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse> createPost(@RequestBody PostRequest request) {
        return ResponseEntity.ok(this.mattermostService.createPost(request));
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> putPost(@RequestBody PostUpdateRequest request) {
        return ResponseEntity.ok(this.mattermostService.putPost(request));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable String postId) {
        return ResponseEntity.ok(this.mattermostService.deletePost(postId));
    }

    @GetMapping("/users/autocomplete")
    public ResponseEntity<List<UserAutocompleteSummary>> getUserAutocomplete(@RequestParam String name) {
        // 영어 외의 문자도 인코딩할 수 있도록 인코딩 코드 추가
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        return ResponseEntity.ok(this.mattermostService.getUserAutocomplete(encodedName));
    }

    // userId를 받아와서 속한 채널을 DB에 저장하는 endpoint
    @GetMapping("/{userId}/channels")
    public ResponseEntity<ApiResponse> saveChannelsByUserId(@PathVariable Long userId) {
        MMChannelSummary[] channelSummaries = this.mattermostService.getChannelsByUserIdFromMM(userId);
        List<Channel> nonDuplicateChannels = this.mattermostService.getNonDuplicateChannels(
                Arrays.asList(channelSummaries));
        return ResponseEntity.ok(this.mattermostService.saveAllChannelsByMMChannelList(nonDuplicateChannels));
    }
}
