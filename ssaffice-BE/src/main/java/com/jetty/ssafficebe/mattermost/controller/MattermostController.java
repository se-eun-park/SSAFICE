package com.jetty.ssafficebe.mattermost.controller;

import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.common.security.userdetails.CustomUserDetails;
import com.jetty.ssafficebe.mattermost.payload.DirectMessageRequest;
import com.jetty.ssafficebe.mattermost.payload.MMChannelSummary;
import com.jetty.ssafficebe.mattermost.payload.PostRequest;
import com.jetty.ssafficebe.mattermost.payload.PostSummary;
import com.jetty.ssafficebe.mattermost.payload.PostUpdateRequest;
import com.jetty.ssafficebe.mattermost.payload.UserAutocompleteSummary;
import com.jetty.ssafficebe.mattermost.service.MattermostService;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // user가 새로고침할 때 동작하는 endPoint
    // 해당 userID를 통해 MM에서 채널리스트를 가져와 공지사항만 필터링 한 후 Channel table과 UserChannel table에 저장

    @GetMapping("/channels")
    public ResponseEntity<ApiResponse> saveChannelsByUserId(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();

        // 1. Channel table에 저장하는 부분
        // 1-1. MM에서 채널리스트를 가져와 공지사항만 필터링 한 후 Channel table에 저장
        List<MMChannelSummary> mmchannelSummaryList = this.mattermostService.getChannelsByUserIdFromMM(userId);
        List<MMChannelSummary> filteredNoticeChannels = this.mattermostService.filteredNoticeChannels(
                mmchannelSummaryList);
        List<Channel> nonDuplicateChannels = this.mattermostService.getNonDuplicateChannels(filteredNoticeChannels);
        this.mattermostService.saveAllChannelsByMMChannelList(nonDuplicateChannels);
        // 1-2. teamId도 업데이트 한다.
        for (Channel channel : nonDuplicateChannels) {
            String teamId = this.mattermostService.getTeamByChannelIdFromMM(userId, channel);
            this.mattermostService.updateTeamByChannelId(teamId, channel.getChannelId());
        }

        // 2. UserChannel table에 저장하는 부분
        List<MMChannelSummary> nonDuplicateChannelsByUserId = this.mattermostService.getNonDuplicateChannelsByUserId(
                userId, filteredNoticeChannels);

        return ResponseEntity.ok(
                this.mattermostService.saveChannelListToUserChannelByUserId(userId, nonDuplicateChannelsByUserId));
    }


    @PostMapping("/remindmessage")
    public ResponseEntity<ApiResponse> sendDirectMessage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @RequestBody DirectMessageRequest request) {

        Long userId = userDetails.getUserId();
        Long targetUserId = request.getTargetUserId();
        Long scheduleId = request.getScheduleId();
        String channelId = this.mattermostService.createDMChannel(userId, targetUserId);
        return ResponseEntity.ok(
                this.mattermostService.sendDirectMessage(userId, channelId, scheduleId));
    }


}
