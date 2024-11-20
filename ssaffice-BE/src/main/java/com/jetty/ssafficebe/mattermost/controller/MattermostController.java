package com.jetty.ssafficebe.mattermost.controller;

import com.jetty.ssafficebe.channel.entity.Channel;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.common.security.userdetails.CustomUserDetails;
import com.jetty.ssafficebe.mattermost.payload.DirectMessageRequest;
import com.jetty.ssafficebe.mattermost.payload.MMChannelSummary;
import com.jetty.ssafficebe.mattermost.payload.MMUserIdRequest;
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

    /**
     * 사용자가 속한 MM 채널 목록을 가져와 해당 채널들을 Channel 테이블과 UserChannel 테이블에 저장하는 메서드입니다.
     * 이 메서드는 두 단계로 구성됩니다:
     * 1. 사용자가 속한 채널을 Mattermost에서 가져와 Channel 테이블에 저장합니다.
     * 2. 사용자와 채널의 연관 관계를 UserChannel 테이블에 저장합니다.
     *
     * @param customUserDetails 인증된 사용자의 세부 정보를 포함하는 객체입니다. 이를 통해 사용자 ID를 가져올 수 있습니다. JWT Token 을 통해 인증된 사용자의 정보를 가져옵니다.
     * @return 요청의 처리 결과를 담고 있는 ResponseEntity 객체로, 성공적으로 저장되었을 때의 응답 결과를 포함합니다.
     */
    @GetMapping("/channels")
    public ResponseEntity<ApiResponse> saveChannelsByUserId(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUserId();
        List<MMChannelSummary> mmchannelSummaryList = this.mattermostService.getChannelsByUserIdFromMM(userId);
        List<MMChannelSummary> filteredNoticeChannels = this.mattermostService.filteredNoticeChannels(
                mmchannelSummaryList);
        List<Channel> nonDuplicateChannels = this.mattermostService.getNonDuplicateChannels(filteredNoticeChannels);
        this.mattermostService.saveAllChannelsByMMChannelList(nonDuplicateChannels);

        List<MMChannelSummary> nonDuplicateChannelsByUserId = this.mattermostService.getNonDuplicateChannelsByUserId(
                userId, filteredNoticeChannels);

        return ResponseEntity.ok(
                this.mattermostService.saveChannelListToUserChannelByUserId(userId, nonDuplicateChannelsByUserId));
    }


    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendDirectMessage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @RequestBody DirectMessageRequest request) {

        Long userId = userDetails.getUserId();
        Long scheduleId = request.getScheduleId();
        List<MMUserIdRequest> userIds = request.getUserIds();

        for (MMUserIdRequest mmUserIdRequest : userIds) {
            Long targetUserId = mmUserIdRequest.getTargetUserId();
            String channelId = this.mattermostService.createDMChannel(userId, targetUserId);
            this.mattermostService.sendDirectMessage(userId, channelId, scheduleId);
        }
        return ResponseEntity.ok(new ApiResponse(true, "메시지 전송 완료", userIds));
    }


}
