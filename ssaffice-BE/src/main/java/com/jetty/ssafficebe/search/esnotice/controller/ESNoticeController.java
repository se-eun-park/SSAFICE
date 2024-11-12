package com.jetty.ssafficebe.search.esnotice.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.search.esnotice.payload.ESNoticeRequest;
import com.jetty.ssafficebe.search.esnotice.service.ESNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/notice")
@RequiredArgsConstructor
public class ESNoticeController {

    private final ESNoticeService esNoticeService;

    @PostMapping
    public ResponseEntity<ApiResponse> saveNotice(@RequestBody ESNoticeRequest request) {
        return ResponseEntity.ok(esNoticeService.saveNotice(request));
    }
}