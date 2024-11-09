package com.jetty.ssafficebe.notice.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.common.security.userdetails.CustomUserDetails;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForList;
import com.jetty.ssafficebe.notice.service.NoticeService;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 공지사항 추가
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveNotice(@RequestPart NoticeRequest noticeRequest,
                                                  @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {
        if (files == null) {
            files = Collections.emptyList();
        }

        return ResponseEntity.ok(noticeService.saveNotice(noticeRequest, files));
    }

    /**
     * 공지사항 삭제
     */
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponse> deleteNotice(@PathVariable Long noticeId) {
        return ResponseEntity.ok(noticeService.deleteNotice(noticeId));
    }

    /**
     * (ROLE_USER) 공지사항 조회
     *  : 내가 속해있는 채널의 공지사항 조회.
     *
     *  TODO : ROLE_USER인 경우에만 접근 가능하도록 security 설정 (일반 관리자 - 프로 접근 불가능)
     */
    @GetMapping
    public ResponseEntity<Page<NoticeSummaryForList>> getNoticeList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(size = 20,
                             sort = "createdAt",
                             direction = Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(noticeService.getNoticeList(userDetails.getUserId(), "ROLE_USER", pageable));
    }

    /**
     * (ROLE_ADMIN) 공지사항 조회
     * : 내가 작성한 공지사항 조회
     * page default size : 20
     * page default sort : 최신순
     *
     * TODO : ROLE_ADMIN인 경우에만 접근 가능하도록 security 설정
     */
    @GetMapping("/admin")
    public ResponseEntity<Page<NoticeSummaryForList>> getNoticeListForAdmin(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(size = 20,
                             sort = "createdAt",
                             direction = Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(noticeService.getNoticeList(userDetails.getUserId(), "ROLE_ADMIN", pageable));
    }
}
