package com.jetty.ssafficebe.notice.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForList;
import com.jetty.ssafficebe.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 공지사항 리스트 조회
     */
    @GetMapping
    public ResponseEntity<Page<NoticeSummaryForList>> getNoticeList(@PageableDefault(size = 20,
                                                                       sort = "createdAt",
                                                                       direction = Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(noticeService.getNoticeList(pageable));
    }
}
