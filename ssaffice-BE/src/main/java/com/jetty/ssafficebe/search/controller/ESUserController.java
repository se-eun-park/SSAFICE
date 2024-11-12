package com.jetty.ssafficebe.search.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.search.payload.ESUserRequest;
import com.jetty.ssafficebe.search.payload.ESUserSearchFilter;
import com.jetty.ssafficebe.search.service.ESUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/user")
@RequiredArgsConstructor
public class ESUserController {

    private final ESUserService esUserService;

    @PostMapping
    public ResponseEntity<ApiResponse> saveUser(@RequestBody ESUserRequest request) {
        return ResponseEntity.ok(esUserService.saveUser(request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.ok(esUserService.deleteUser(userId));
    }

    // TODO : 로직 구현 및 Response Type 설정
    @PostMapping("/{channelId}")
    public ResponseEntity<Page<?>> searchUser(@RequestParam String channelId,
                                              @RequestBody ESUserSearchFilter request) {
        return ResponseEntity.ok().build();
    }

}