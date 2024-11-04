package com.jetty.ssafficebe.notice.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 공지사항 추가
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveNotice(@RequestBody NoticeRequest noticeRequest) {
        return ResponseEntity.ok(noticeService.saveNotice(noticeRequest));
    }

    /**
     * 공지사항 삭제
     */
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponse> deleteNotice(@PathVariable Long noticeId) {
        return ResponseEntity.ok(noticeService.deleteNotice(noticeId));
    }
}
