package com.jetty.ssafficebe.schedule.controller;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummaryForList;
import com.jetty.ssafficebe.schedule.service.ScheduleService;
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
     * @param scheduleRequest : 일정 정보 + 공지사항 id(개인 일정의 경우: null) + 리마인드 정보
     * @return 등록된 일정 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        ApiResponse apiResponse = scheduleService.saveSchedule(scheduleRequest);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * 일정 수정
     *
     * @param scheduleId      : 수정할 일정 id
     * @param scheduleRequest : schedule 정보
     * @return 수정된 일정 정보
     * <p>
     * TODO: 기존 리마인드 전부 삭제 후 다시 추가
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse> updateSchedule(@PathVariable("scheduleId") Long scheduleId,
                                                      @RequestBody ScheduleRequest scheduleRequest) {
        ApiResponse apiResponse = scheduleService.updateSchedule(scheduleId, scheduleRequest);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * 일정 조회
     *
     * @param scheduleId : 조회할 일정 id
     * @return 조회한 일정 정보
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse> getSchedule(@PathVariable("scheduleId") Long scheduleId) {
        ApiResponse apiResponse = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * 일정 삭제
     *
     * @param scheduleId : 삭제할 일정 id
     * @return 삭제된 일정 id
     * TODO : 공지 파생에 따라서 처리 방식 적용
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse> deleteSchedule(@PathVariable("scheduleId") Long scheduleId) {
        ApiResponse apiResponse = scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * 일정 조회
     *
     * @param pageable              : 20개씩 / 마감 임박순
     * @param scheduleFilterRequest :미등록 공지 여부, 상태, 일정 출처
     * @return 조건에 맞는 일정 리스트
     * TODO : 현재 로그인한 id 에 맞는 일정 인지 로직 추가
     */
    @GetMapping
    public ResponseEntity<Page<ScheduleSummaryForList>> getScheduleList(
            @RequestBody ScheduleFilterRequest scheduleFilterRequest,
            @PageableDefault(size = 20, sort = "endDateTime", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(scheduleService.getScheduleList(scheduleFilterRequest, pageable));
    }
}
