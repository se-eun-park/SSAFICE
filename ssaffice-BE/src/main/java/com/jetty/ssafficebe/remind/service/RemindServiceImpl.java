package com.jetty.ssafficebe.remind.service;

import com.jetty.ssafficebe.common.exception.ErrorCode;
import com.jetty.ssafficebe.common.exception.exceptiontype.DuplicateValueException;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidAuthorizationException;
import com.jetty.ssafficebe.common.exception.exceptiontype.InvalidValueException;
import com.jetty.ssafficebe.common.exception.exceptiontype.ResourceNotFoundException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.remind.converter.RemindConverter;
import com.jetty.ssafficebe.remind.entity.Remind;
import com.jetty.ssafficebe.remind.payload.RemindRequest;
import com.jetty.ssafficebe.remind.repository.RemindRepository;
import com.jetty.ssafficebe.role.repository.UserRoleRepository;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.repository.ScheduleRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RemindServiceImpl implements RemindService {

    private final RemindRepository remindRepository;
    private final RemindConverter remindConverter;
    private final ScheduleRepository scheduleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public ApiResponse saveRemind(Long userId, RemindRequest remindRequest) {
        // ! 1. 일정 조회 및 권한 검증
        Schedule schedule = findAndValidateSchedule(userId, remindRequest.getScheduleId(), "create");

        // ! 2. 알림 타입 검증
        if (!Arrays.asList("DAILY", "ONCE").contains(remindRequest.getRemindTypeCd())) {
            throw new InvalidValueException(ErrorCode.INVALID_USAGE, "remindTypeCd", remindRequest.getRemindTypeCd());
        }

        // ! 3. 알림 중복 체크
        if (remindRepository.existsDuplicateRemind(schedule.getScheduleId(), remindRequest.getRemindTypeCd(), remindRequest.getRemindDateTime())) {
            String errorMessage = "DAILY".equals(remindRequest.getRemindTypeCd()) ? "해당 시간에 이미 매일 알림이 설정되어 있습니다." : "이미 해당 시점에 알림이 설정되어 있습니다.";
            throw new DuplicateValueException(ErrorCode.REMIND_ALREADY_EXISTS, errorMessage, remindRequest.getRemindDateTime().toString());
        }

        // ! 4. Remind 생성 및 저장
        Remind remind = remindConverter.toRemind(remindRequest);
        schedule.addRemind(remind);
        Remind savedRemind = remindRepository.save(remind);

        return new ApiResponse(true, "알림 등록에 성공하였습니다.", remindConverter.toRemindSummary(savedRemind));
    }

    @Override
    public ApiResponse deleteRemind(Long userId, Long remindId) {
        // ! 1. Remind 조회
        Remind remind = remindRepository.findById(remindId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.REMIND_NOT_FOUND, "해당 알림을 찾을 수 없습니다.", remindId.toString()));

        // ! 2. 일정 조회 및 권한 검증
        findAndValidateSchedule(userId, remind.getScheduleId(), "delete");

        // ! 4. 알림 삭제
        remindRepository.delete(remind);
        return new ApiResponse(true, "알림이 성공적으로 삭제되었습니다.", remindId);
    }

    private Schedule findAndValidateSchedule(Long userId, Long scheduleId, String type) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException(
                ErrorCode.SCHEDULE_NOT_FOUND, "해당 일정을 찾을 수 없습니다.", scheduleId));

        boolean isAdmin = userRoleRepository.existsByUserIdAndRoleIdIn(userId, Arrays.asList("ROLE_ADMIN", "ROLE_SYSADMIN"));

        if (!schedule.getUserId().equals(userId) && !isAdmin) {
            ErrorCode errorCode = type.equals("create") ? ErrorCode.REMIND_CREATE_FORBIDDEN : ErrorCode.REMIND_DELETE_FORBIDDEN;
            String errorMessage = type.equals("delete") ? "알림 등록 권한이 없습니다." : "알림 삭제 권한이 없습니다.";
            throw new InvalidAuthorizationException(errorCode, errorMessage, userId);
        }

        return schedule;
    }
}
