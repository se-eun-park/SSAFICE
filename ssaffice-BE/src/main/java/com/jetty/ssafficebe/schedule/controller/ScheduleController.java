package com.jetty.ssafficebe.schedule.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 등록
     *
     * @param scheduleRequest : 일정 정보 + 공지사항 id(필수 공지 파생의 경우: null) + 리마인드 정보
     * @return 일정 + 공지사항 + 리마인드 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        ApiResponse apiResponse = scheduleService.saveSchedule(scheduleRequest);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * 일정 수정
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse> updateSchedule(@PathVariable("scheduleId") String scheduleId,
                                                      @RequestBody ScheduleRequest scheduleRequest) {
        ApiResponse apiResponse = scheduleService.updateSchedule(scheduleId, scheduleRequest);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /*
     * 일정 리스트 조회
     */

    /*
     * 일정 상세 조회
     */

    /*
     * 일정 삭제
     */

    /*
     * 일정 검색
     */
}
