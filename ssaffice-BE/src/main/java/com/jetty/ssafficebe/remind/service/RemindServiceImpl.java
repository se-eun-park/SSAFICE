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
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public ApiResponse saveRemind(Long userId, RemindRequest remindRequest) {
        Schedule schedule = scheduleRepository.findById(remindRequest.getScheduleId())
                                              .orElseThrow(() -> new ResourceNotFoundException(
                                                      ErrorCode.SCHEDULE_NOT_FOUND,
                                                      "해당 일정을 찾을 수 없습니다.",
                                                      remindRequest.getScheduleId()));

        // 일정 작성자 권한 검증
        if (!schedule.getUserId().equals(userId)) {
            throw new InvalidAuthorizationException(
                    ErrorCode.INVALID_AUTHORIZATION,
                    "userId",
                    userId);
        }

        // DAILY 타입인 경우
        if ("DAILY".equals(remindRequest.getType())) {
            LocalDateTime currentDateTime = remindRequest.getRemindDateTime();
            List<Long> remindIds = new ArrayList<>();
            while (!currentDateTime.isAfter(schedule.getEndDateTime())) {
                Remind savedRemind = saveSingleRemind(currentDateTime, schedule);
                remindIds.add(savedRemind.getRemindId());
                currentDateTime = currentDateTime.plusDays(1);
            }
            return new ApiResponse(true, "일별 리마인드 등록에 성공하였습니다.", remindIds);
        } else if ("ONCE".equals(remindRequest.getType())) {
            // 일반 리마인드인 경우
            Remind savedRemind = saveSingleRemind(remindRequest.getRemindDateTime(), schedule);
            return new ApiResponse(true, "리마인드 등록에 성공하였습니다.", savedRemind.getRemindId());
        } else {
            // 잘못 들어온 경우
            throw new InvalidValueException(ErrorCode.INVALID_USAGE, "usage", remindRequest.getType());
        }
    }

    @Override
    public ApiResponse deleteRemind(Long userId, Long remindId) {
        Remind remind = remindRepository.findById(remindId)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                ErrorCode.REMIND_NOT_FOUND,
                                                "해당 알림을 찾을 수 없습니다.",
                                                remindId.toString()));
        // 리마인드 소유자 권한 검증
        Schedule schedule = remind.getSchedule();
        if (!schedule.getUserId().equals(userId)) {
            throw new InvalidAuthorizationException(
                    ErrorCode.INVALID_AUTHORIZATION,
                    "userId",
                    userId);
        }

        // Essential 리마인드 삭제 방지
        if ("Y".equals(remind.getIsEssentialYn())) {
            throw new InvalidValueException(
                    ErrorCode.INVALID_REMIND_OPERATION,
                    "필수 리마인드는 삭제할 수 없습니다.",
                    remindId.toString()
            );
        }

        remindRepository.delete(remind);
        return new ApiResponse(true, "리마인드가 성공적으로 삭제되었습니다.", remindId);
    }

    private Remind saveSingleRemind(LocalDateTime remindDateTime, Schedule schedule) {
        if (remindRepository.existsByScheduleIdAndRemindDateTime(schedule.getScheduleId(), remindDateTime)) {
            throw new DuplicateValueException(
                    ErrorCode.REMIND_ALREADY_EXISTS,
                    "이미 해당 시간에 알림이 존재합니다.",
                    remindDateTime.toString());
        }

        RemindRequest singleRequest = new RemindRequest();
        singleRequest.setScheduleId(schedule.getScheduleId());
        singleRequest.setRemindDateTime(remindDateTime);

        Remind remind = remindConverter.toRemind(singleRequest);
        schedule.addRemind(remind);
        return remindRepository.save(remind);
    }
}
