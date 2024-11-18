package com.jetty.ssafficebe.notice.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.common.security.userdetails.CustomUserDetails;
import com.jetty.ssafficebe.notice.payload.NoticeDetail;
import com.jetty.ssafficebe.notice.payload.NoticeFilterRequest;
import com.jetty.ssafficebe.notice.payload.NoticeRequest;
import com.jetty.ssafficebe.notice.payload.NoticeSummary;
import com.jetty.ssafficebe.notice.payload.NoticeSummaryForAdmin;
import com.jetty.ssafficebe.notice.service.NoticeService;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * <p>
     */
    @PostMapping("/admin")
    public ResponseEntity<ApiResponse> saveNotice(@RequestPart NoticeRequest noticeRequest,
                                                  @RequestPart(value = "files", required = false) List<MultipartFile> files)
            throws IOException {
        if (files == null) {
            files = Collections.emptyList();
        }

        return ResponseEntity.ok(noticeService.saveNotice(noticeRequest, files));
    }

    /**
     * 공지사항 삭제
     * <p>
     */
    @DeleteMapping("/admin/{noticeId}")
    public ResponseEntity<ApiResponse> deleteNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @PathVariable Long noticeId) {
        return ResponseEntity.ok(noticeService.deleteNotice(userDetails.getUserId(), noticeId));
    }

    /**
     * 전체 공지사항 조회 : 내가 속해있는 채널의 공지사항을 전체 조회
     */
    @GetMapping
    public ResponseEntity<Page<NoticeSummary>> getNoticeList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PageableDefault(size = 20,
                             sort = "createdAt",
                             direction = Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(noticeService.getNoticePage(userDetails.getUserId(), pageable));
    }

    /**
     * 공지사항 상세 조회
     */
    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDetail> getNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @PathVariable Long noticeId) {
        return ResponseEntity.ok(noticeService.getNotice(userDetails.getUserId(), noticeId));
    }

    /**
     * 내가 작성한 공지사항 리스트 조회
     */
    @GetMapping("/admin/my")
    public ResponseEntity<List<NoticeSummaryForAdmin>> getMyNoticeList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody NoticeFilterRequest noticeFilterRequest,
            @SortDefault(sort = "endDateTime", direction = Sort.Direction.ASC) Sort sort) {
        return ResponseEntity.ok(
                noticeService.getNoticePageByCreateUser(userDetails.getUserId(), noticeFilterRequest, sort));
    }
}
