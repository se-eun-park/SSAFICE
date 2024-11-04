package com.jetty.ssafficebe.schedule.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
@Log4j2
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 등록
     */
    @PostMapping
    public ResponseEntity<?> addSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        ApiResponse apiResponse = scheduleService.addSchedule(scheduleRequest);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        log.info("[schedule] test");
        return ResponseEntity.ok("This is a test API endpoint");
    }
    /**
     * 일정 수정
     */

    /**
     * 일정 리스트 조회
     */

    /**
     * 일정 조회
     */

    /**
     * 일정 삭제
     */

    /**
     * 일정 검색
     */
}
