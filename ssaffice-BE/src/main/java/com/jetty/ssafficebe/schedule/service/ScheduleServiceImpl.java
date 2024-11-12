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
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.payload.ScheduleSummary;
import com.jetty.ssafficebe.schedule.repository.ScheduleRepository;
import com.jetty.ssafficebe.user.entity.User;
import com.jetty.ssafficebe.user.payload.CreatedBySummary;
import com.jetty.ssafficebe.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
        validateAuthorization(userId, scheduleRequest.getUserId());

        // ! 2. Schedule 엔티티 생성 및 연관관계 설정
        Schedule schedule = scheduleConverter.toSchedule(scheduleRequest);
        schedule.setIsEnrollYn("Y");

        // 일정 소유자 설정
        schedule.setUser(userRepository.findById(scheduleRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.USER_NOT_FOUND, "해당 사용자를 찾을 수 없습니다.", scheduleRequest.getUserId())));

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
        validateAuthorization(userId, scheduleRequest.getUserId());

        // ! 3. Schedule 기본 정보 업데이트
        schedule.setTitle(scheduleRequest.getTitle());
        schedule.setMemo(scheduleRequest.getMemo());
        schedule.setStartDateTime(scheduleRequest.getStartDateTime());
        schedule.setEndDateTime(scheduleRequest.getEndDateTime());
        schedule.setScheduleSourceTypeCd(scheduleRequest.getScheduleSourceTypeCd());
        schedule.setScheduleStatusTypeCd(scheduleRequest.getScheduleStatusTypeCd());
        schedule.setIsEnrollYn("Y");

        // ! 4. Remind 정보 갱신
        remindRepository.deleteByScheduleId(scheduleId);
        schedule.getReminds().clear();
        saveScheduleReminds(userId, scheduleRequest.getRemindRequests(), schedule);

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
        validateAuthorization(userId, schedule.getUser().getUserId());

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
        validateAuthorization(userId, schedule.getUser().getUserId());

        // ! 3. 관리자 권한 검증
        boolean isAdmin = userRoleRepository.existsByUserIdAndRoleIdIn(userId, Arrays.asList("ROLE_ADMIN", "ROLE_SYSADMIN"));

        if (!schedule.getIsEssential() && !isAdmin) {
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
    public Page<ScheduleSummary> getScheduleList(ScheduleFilterRequest filterRequest, Pageable pageable) {
        log.info("[Schedule] 일정 목록 조회 시작 - filter=[isEnroll={}, sourceType={}, statusType={}, startDt={}, endDt={}], page={}, size={}",
                filterRequest.getIsEnrollYn(),
                filterRequest.getScheduleSourceTypeCd(),
                filterRequest.getScheduleStatusTypeCd(),
                filterRequest.getFilterStartDateTime(),
                filterRequest.getFilterEndDateTime(),
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<Schedule> schedulesPage = scheduleRepository.getSchedulesByFilter(filterRequest, pageable);

        Page<ScheduleSummary> result = schedulesPage.map(schedule -> {
            ScheduleSummary summary = scheduleConverter.toScheduleSummary(schedule);

            if (schedule.getNotice() != null && schedule.getNotice().getCreateUser() != null) {
                User createUser = schedule.getNotice().getCreateUser();
                summary.setCreateUser(CreatedBySummary.builder()
                                                      .userId(createUser.getUserId())
                                                      .name(createUser.getName())
                                                      .email(createUser.getEmail())
                                                      .profileImgUrl(createUser.getProfileImgUrl())
                                                      .build());
            }

            return summary;
        });

        log.info("[Schedule] 일정 목록 조회 완료 - totalElements={}, totalPages={}", result.getTotalElements(), result.getTotalPages());
        return result;
    }

    /**
     * 관리자 공지사항 등록 시 해당 교육생들에게 일정을 추가해주는 메서드
     */
    @Override
    public void saveSchedulesForUsers(List<Long> userIds, Long noticeId) {
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