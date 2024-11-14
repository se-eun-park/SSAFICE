package com.jetty.ssafficebe.mattermost.controller;

import com.jetty.ssafficebe.mattermost.payload.PostRequest;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.mattermost.payload.PostSummary;
import com.jetty.ssafficebe.mattermost.payload.PostUpdateRequest;
import com.jetty.ssafficebe.mattermost.payload.TeamSummary;
import com.jetty.ssafficebe.mattermost.payload.UserAutocompleteSummary;
import com.jetty.ssafficebe.mattermost.service.MattermostService;
import com.jetty.ssafficebe.mattermost.util.MattermostUtil;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

  @GetMapping("/teams")
  public ResponseEntity<String> getTeamSummary() {
    return ResponseEntity.ok(this.mattermostService.getTeams());
//    return ResponseEntity.ok("getUserAutocomplete");
  }

  @GetMapping("/{userId}/channels")
  public ResponseEntity<String> getChannelsByUserId(@PathVariable Long userId) {
    return ResponseEntity.ok(this.mattermostService.getChannelsByUserId(userId));
  }
}
