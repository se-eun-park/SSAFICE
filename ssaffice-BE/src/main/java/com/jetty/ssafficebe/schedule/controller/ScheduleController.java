package com.jetty.ssafficebe.schedule.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.common.security.userdetails.CustomUserDetails;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleDetail;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummary;
import com.jetty.ssafficebe.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 등록
     *
     * @param scheduleRequest : 일정 정보 + 공지사항 id(개인 일정의 경우: null) + 리마인드 정보
     * @return 등록된 일정 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveSchedule(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @RequestBody ScheduleRequest scheduleRequest) {
        ApiResponse apiResponse = scheduleService.saveSchedule(userDetails.getUserId(), scheduleRequest);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * 일정 수정
     *
     * @param scheduleId      : 수정할 일정 id
     * @param scheduleRequest : schedule 정보
     * @return 수정된 일정 정보
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse> updateSchedule(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @PathVariable("scheduleId") Long scheduleId,
                                                      @RequestBody ScheduleRequest scheduleRequest) {
        ApiResponse apiResponse = scheduleService.updateSchedule(userDetails.getUserId(), scheduleId, scheduleRequest);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * 일정 조회
     *
     * @param scheduleId : 조회할 일정 id
     * @return 조회한 일정 정보
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDetail> getSchedule(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.getSchedule(userDetails.getUserId(), scheduleId));
    }

    /**
     * 일정 삭제
     *
     * @param scheduleId : 삭제할 일정 id
     * @return 삭제된 일정 id
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse> deleteSchedule(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(userDetails.getUserId(), scheduleId));
    }

    /**
     * (ROLE_USER) 개인 일정 조회
     *
     * @param pageable              : 20개씩 / 마감 임박순
     * @param scheduleFilterRequest :미등록 공지 여부, 상태, 일정 출처
     * @return 조건에 맞는 일정 리스트
     * TODO : (개인) 현재 로그인한 id 에 맞는 일정 리스트
     */
    @GetMapping
    public ResponseEntity<Page<ScheduleSummary>> getScheduleList(
            @RequestBody ScheduleFilterRequest scheduleFilterRequest,
            @PageableDefault(size = 20, sort = "endDateTime", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(scheduleService.getScheduleList(scheduleFilterRequest, pageable));
    }

    /**
     * (ROLE_ADMIN) 관리자의 일정 조회
     * TODO : 관리자가 작성한 공지에 대한 해당 유저들의 일정 조회 (등록 여부, 완료 여부, 등 ...)
     */
}
