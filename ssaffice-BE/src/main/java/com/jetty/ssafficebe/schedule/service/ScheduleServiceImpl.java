package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.DuplicateValueException;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidAuthorizationException;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.notice.repository.NoticeRepository;
import com.jetty.ssafficebe.remind.payload.RemindRequest;
import com.jetty.ssafficebe.remind.repository.RemindRepository;
import com.jetty.ssafficebe.remind.service.RemindService;
import com.jetty.ssafficebe.role.repository.UserRoleRepository;
import com.jetty.ssafficebe.schedule.code.ScheduleSourceType;
import com.jetty.ssafficebe.schedule.converter.ScheduleConverter;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleDetail;
import com.jetty.ssafficebe.schedule.payload.ScheduleFilterRequest;
import com.jetty.ssafficebe.schedule.payload.SchedulePageResponse;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummary;
import com.jetty.ssafficebe.schedule.repository.ScheduleRepository;
import com.jetty.ssafficebe.user.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleConverter scheduleConverter;
    private final ScheduleRepository scheduleRepository;
    private final RemindRepository remindRepository;
    private final RemindService remindService;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public ApiResponse saveSchedule(Long userId, ScheduleRequest scheduleRequest) {
        log.info("[Schedule] 일정 등록 시작 - userId={}, requestUserId={}", userId, scheduleRequest.getUserId());

        // ! 1. 권한 검증
        Long targetUserId = scheduleRequest.getUserId() != null ? scheduleRequest.getUserId() : userId;
        validateAuthorization(userId, targetUserId);

        // ! 2. Schedule 엔티티 생성 및 연관관계 설정
        Schedule schedule = scheduleConverter.toSchedule(scheduleRequest);
        schedule.setIsEnrollYn("Y");

        // 일정 소유자 설정
        schedule.setUserId(targetUserId);
        schedule.setUser(userRepository.findById(targetUserId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.USER_NOT_FOUND, "해당 사용자를 찾을 수 없습니다.", targetUserId)));

        if (scheduleRequest.getNoticeId() != null) {
            schedule.setNotice(noticeRepository.findById(scheduleRequest.getNoticeId()).orElseThrow(() -> new ResourceNotFoundException(
                    ErrorCode.NOTICE_NOT_FOUND, "해당 공지사항을 찾을 수 없습니다.", scheduleRequest.getNoticeId())));
        }

        // ! 3. Schedule 저장
        Schedule savedSchedule = scheduleRepository.save(schedule);
        log.info("[Schedule] 일정 등록 완료 - scheduleId={}, title={}", savedSchedule.getScheduleId(), savedSchedule.getTitle());

        // ! 4. Remind 엔티티 생성 및 연관관계 설정
        saveScheduleReminds(userId, scheduleRequest.getRemindRequests(), savedSchedule);

        // ! 5. Response 반환
        return new ApiResponse(true, "일정 등록에 성공하였습니다.", savedSchedule.getScheduleId());
    }


    @Override
    public ApiResponse updateSchedule(Long userId, Long scheduleId, ScheduleRequest scheduleRequest) {
        log.info("[Schedule] 일정 수정 시작 - scheduleId={}, userId={}, requestUserId={}", scheduleId, userId, scheduleRequest.getUserId());

        // ! 1. 수정할 Schedule 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.SCHEDULE_NOT_FOUND, "해당 일정을 찾을 수 없습니다.", scheduleId));

        // ! 2. 권한 검증
        validateAuthorization(userId, schedule.getUserId());

        // ! 3. Schedule 기본 정보 업데이트 - 요청에 있는 필드만 수정
        Optional.ofNullable(scheduleRequest.getTitle())
                .ifPresent(schedule::setTitle);
        Optional.ofNullable(scheduleRequest.getMemo())
                .ifPresent(schedule::setMemo);
        Optional.ofNullable(scheduleRequest.getStartDateTime())
                .ifPresent(schedule::setStartDateTime);
        Optional.ofNullable(scheduleRequest.getEndDateTime())
                .ifPresent(schedule::setEndDateTime);
        Optional.ofNullable(scheduleRequest.getScheduleSourceTypeCd())
                .ifPresent(schedule::setScheduleSourceTypeCd);
        Optional.ofNullable(scheduleRequest.getScheduleStatusTypeCd())
                .ifPresent(schedule::setScheduleStatusTypeCd);
        Optional.ofNullable(scheduleRequest.getIsEssentialYn())
                .ifPresent(schedule::setIsEssentialYn);
        Optional.ofNullable(scheduleRequest.getIsEnrollYn())
                .ifPresent(schedule::setIsEnrollYn);

        // ! 4. Remind 정보 갱신 - reminds 요청에 있을 때만 수정
        if (scheduleRequest.getRemindRequests() != null) {
            remindRepository.deleteByScheduleId(scheduleId);
            schedule.getReminds().clear();
            saveScheduleReminds(userId, scheduleRequest.getRemindRequests(), schedule);
        }

        // ! 5. Schedule 저장 및 Response 반환
        Schedule savedSchedule = scheduleRepository.save(schedule);
        log.info("[Schedule] 일정 수정 완료 - scheduleId={}, title={}", savedSchedule.getScheduleId(), savedSchedule.getTitle());
        return new ApiResponse(true, "일정 수정에 성공하였습니다.", schedule.getScheduleId());
    }

    @Override
    public ScheduleDetail getSchedule(Long userId, Long scheduleId) {
        log.info("[Schedule] 일정 조회 시작 - scheduleId={}, userId={}", scheduleId, userId);

        // ! 1. 조회할 일정 찾기
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.SCHEDULE_NOT_FOUND, "해당 일정을 찾을 수 없습니다.", scheduleId));

        // ! 2. 권한 검증
        validateAuthorization(userId, schedule.getUserId());

        // ! 3. Response 반환
        log.info("[Schedule] 일정 조회 완료 - scheduleId={}, title={}", scheduleId, schedule.getTitle());
        return scheduleConverter.toScheduleDetail(schedule);
    }

    @Override
    public ApiResponse deleteSchedule(Long userId, Long scheduleId) {
        log.info("[Schedule] 일정 삭제 시작 - scheduleId={}, userId={}", scheduleId, userId);

        // ! 1. 삭제할 일정 찾기
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.SCHEDULE_NOT_FOUND, "해당 일정을 찾을 수 없습니다.", scheduleId));

        // ! 2. 권한 검증
        validateAuthorization(userId, schedule.getUserId());

        // ! 3. 관리자 권한 검증
        boolean isAdmin = userRoleRepository.existsByUserIdAndRoleIdIn(userId, Arrays.asList("ROLE_ADMIN", "ROLE_SYSADMIN"));

        if (schedule.getIsEssential() && !isAdmin) {
            throw new InvalidAuthorizationException(ErrorCode.INVALID_AUTHORIZATION, "scheduleId", scheduleId);
        }

        // ! 4. 팀 공지 일정 처리 및 Response 반환
        if (schedule.getNotice() != null && ScheduleSourceType.TEAM.name().equals(schedule.getScheduleSourceTypeCd())) {
            schedule.setIsEnrollYn("N");
            scheduleRepository.save(schedule);
            return new ApiResponse(true, "팀 공지 일정이 미등록 처리되었습니다.", scheduleId);
        }

        // ! 5. 개인 일정 삭제 및 Response 반환
        scheduleRepository.delete(schedule);
        log.info("[Schedule] 일정 삭제 완료 - scheduleId={}", scheduleId);

        return new ApiResponse(true, "일정 삭제에 성공하였습니다.", scheduleId);
    }

    @Override
    public SchedulePageResponse getScheduleList(Long userId, ScheduleFilterRequest filterRequest, Pageable pageable) {
        log.info("[Schedule] 일정 목록 조회 시작 - filter=[isEnroll={}, sourceType={}, statusType={}, startDt={}, endDt={}], page={}, size={}",
                filterRequest.getIsEnrollYn(),
                filterRequest.getScheduleSourceTypeCd(),
                filterRequest.getScheduleStatusTypeCd(),
                filterRequest.getFilterStartDateTime(),
                filterRequest.getFilterEndDateTime(),
                pageable.getPageNumber(),
                pageable.getPageSize());

        // ! 1. 해당 공지사항의 전체 일정들 조회하여 상태 카운트 계산
        List<Schedule> allNoticeSchedules = scheduleRepository.findAllByUserId(userId);

        long todoCount = allNoticeSchedules.stream()
                                           .filter(s -> "TODO".equals(s.getScheduleStatusTypeCd()))
                                           .count();

        long inProgressCount = allNoticeSchedules.stream()
                                                 .filter(s -> "IN_PROGRESS".equals(s.getScheduleStatusTypeCd()))
                                                 .count();

        long doneCount = allNoticeSchedules.stream()
                                           .filter(s -> "DONE".equals(s.getScheduleStatusTypeCd()))
                                           .count();

        List<Integer> statusCounts = Arrays.asList(
                (int) todoCount,
                (int) inProgressCount,
                (int) doneCount
        );

        // ! 2. 조건에 맞는 일정 리스트 생성
        Page<Schedule> schedulesPage = scheduleRepository.findSchedulesByUserIdAndFilter(userId, filterRequest, pageable);

        // ! 3. 응답 생성
        Page<ScheduleSummary> result = schedulesPage.map(scheduleConverter::toScheduleSummary);

        log.info("[Schedule] 일정 목록 조회 완료 - totalElements={}, totalPages={}", result.getTotalElements(), result.getTotalPages());
        return scheduleConverter.toSchedulePageResponse(result, statusCounts);
    }

    /**
     * 관리자 공지사항 등록 시 해당 교육생들에게 일정을 추가해주는 메서드
     */
    @Override
    public void saveSchedulesForUsers(Long noticeId, List<Long> userIds) {
        log.info("[Schedule] 공지사항 일정 일괄 생성 시작 - noticeId={}, userCount={}", noticeId, userIds.size());

        // ! 1. 공지사항 조회
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.NOTICE_NOT_FOUND, "해당 공지사항을 찾을 수 없습니다.", noticeId));

        // ! 2. Schedule 엔티티 리스트 생성 및 일괄 저장
        List<Schedule> schedules = userIds.stream()
                                          .map(userId -> {
                                              Schedule schedule = scheduleConverter.toSchedule(userId, noticeId);
                                              schedule.setNotice(notice);
                                              return schedule;
                                          })
                                          .toList();
        List<Schedule> savedSchedules = scheduleRepository.saveAll(schedules);

        log.info("[Schedule] 공지사항 일정 일괄 생성 완료 - 전체={}, 성공={}", userIds.size(), savedSchedules.size());
    }

    /**
     * [필터 O]
     * 관리자 공지사항 조회 시 해당 교육생들의 일정을 조회하는 메서드
     */
    @Override
    public SchedulePageResponse getSchedulesByNoticeForAdmin(Long noticeId, ScheduleFilterRequest filterRequest, Pageable pageable) {
        log.info("[Schedule] 공지사항 관련 교육생 일정 필터 조회 시작 - noticeId={}", noticeId);

        // ! 1. 해당 공지사항의 전체 일정들 조회하여 상태 카운트 계산
        List<Schedule> allNoticeSchedules = scheduleRepository.findAllByNoticeId(noticeId);

        long todoCount = allNoticeSchedules.stream()
                                           .filter(s -> "TODO".equals(s.getScheduleStatusTypeCd()))
                                           .count();

        long inProgressCount = allNoticeSchedules.stream()
                                                 .filter(s -> "IN_PROGRESS".equals(s.getScheduleStatusTypeCd()))
                                                 .count();

        long doneCount = allNoticeSchedules.stream()
                                           .filter(s -> "DONE".equals(s.getScheduleStatusTypeCd()))
                                           .count();

        List<Integer> statusCounts = Arrays.asList(
                (int) todoCount,
                (int) inProgressCount,
                (int) doneCount
        );

        // ! 2. 해당 공지사항의 일정들 필터 조회
        Page<Schedule> schedulePage = scheduleRepository.findSchedulesByNoticeIdAndFilter(noticeId, filterRequest, pageable);

        // ! 3. ScheduleSummary 로 변환
        Page<ScheduleSummary> result = schedulePage.map(scheduleConverter::toScheduleSummary);

        log.info("[Schedule] 공지사항 관련 교육생 일정 필터 조회 완료 - noticeId={}, count={}",
                noticeId, result.getTotalElements());

        return scheduleConverter.toSchedulePageResponse(result, statusCounts);
    }

    /**
     * [필터 X]
     * 관리자 공지사항 조회 시 해당 교육생들의 일정을 조회하는 메서드
     */
    @Override
    public SchedulePageResponse getSchedulesByNoticeForAdmin(Long noticeId, Pageable pageable) {
        log.info("[Schedule] 공지사항 관련 교육생 일정 조회 시작 - noticeId={}", noticeId);

        // ! 1. 해당 공지사항의 전체 일정들 조회하여 상태 카운트 계산
        List<Schedule> allNoticeSchedules = scheduleRepository.findAllByNoticeId(noticeId);

        long todoCount = allNoticeSchedules.stream()
                                           .filter(s -> "TODO".equals(s.getScheduleStatusTypeCd()))
                                           .count();

        long inProgressCount = allNoticeSchedules.stream()
                                                 .filter(s -> "IN_PROGRESS".equals(s.getScheduleStatusTypeCd()))
                                                 .count();

        long doneCount = allNoticeSchedules.stream()
                                           .filter(s -> "DONE".equals(s.getScheduleStatusTypeCd()))
                                           .count();

        List<Integer> statusCounts = Arrays.asList(
                (int) todoCount,
                (int) inProgressCount,
                (int) doneCount
        );

        // ! 2. 해당 공지사항의 일정들 조회
        Page<Schedule> schedulePage = scheduleRepository.findSchedulesByNoticeId(noticeId, pageable);

        // ! 3. ScheduleSummary 로 변환
        Page<ScheduleSummary> result = schedulePage.map(scheduleConverter::toScheduleSummary);

        log.info("[Schedule] 공지사항 관련 교육생 일정 조회 완료 - noticeId={}, count={}",
                noticeId, result.getTotalElements());

        return scheduleConverter.toSchedulePageResponse(result, statusCounts);
    }

    /**
     * 요청한 사용자가 일정 소유자이거나 관리자인 경우만 허용하는 메서드
     */
    private void validateAuthorization(Long userId, Long requestUserId) {
        boolean isAdmin = userRoleRepository.existsByUserIdAndRoleIdIn(userId, Arrays.asList("ROLE_ADMIN", "ROLE_SYSADMIN"));

        if (!requestUserId.equals(userId) && !isAdmin) {
            log.warn("[Authorization] 권한 없음 - userId={}, requestUserId={}, isAdmin={}", userId, requestUserId, isAdmin);
            throw new InvalidAuthorizationException(ErrorCode.INVALID_AUTHORIZATION, "userId", userId);
        }
        log.info("[Authorization] 권한 검증 완료 - userId={}, requestUserId={}, isAdmin={}", userId, requestUserId, isAdmin);
    }

    /**
     * 해당 일정에 알람을 생성/저장 하는 메서드
     */
    private void saveScheduleReminds(Long userId, List<RemindRequest> remindRequests, Schedule schedule) {
        if (remindRequests == null) return;

        log.info("[Schedule] 알림 등록 시작 - scheduleId={}, remindCount={}", schedule.getScheduleId(), remindRequests.size());
        remindRequests.forEach(remindRequest -> {
            remindRequest.setScheduleId(schedule.getScheduleId());
            try {
                remindService.saveRemind(userId, remindRequest);
            } catch (DuplicateValueException e) {
                log.warn("[Schedule] 알림 중복 발생 - scheduleId={}, remindTime={}", schedule.getScheduleId(), remindRequest.getRemindDateTime());
                if (e.getErrorCode() != ErrorCode.REMIND_ALREADY_EXISTS) {
                    throw e;
                }
            }
        });
        log.info("[Schedule] 알림 등록 완료 - scheduleId={}", schedule.getScheduleId());
    }
}