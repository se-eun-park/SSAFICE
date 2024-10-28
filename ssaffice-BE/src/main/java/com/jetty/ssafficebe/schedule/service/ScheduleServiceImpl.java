package com.jetty.ssafficebe.schedule.service;

import com.jetty.ssafficebe.common.payload.ApiResponse;
import com.jetty.ssafficebe.schedule.converter.ScheduleConverter;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.schedule.payload.ScheduleRequest;
import com.jetty.ssafficebe.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleConverter scheduleConverter;
    private final ScheduleRepository scheduleRepository;

    @Override
    public ApiResponse addSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleConverter.toSchedule(scheduleRequest);
        scheduleRepository.save(schedule);

        return new ApiResponse(true, "일정 등록에 성공하였습니다.", scheduleConverter.toScheduleResponse(schedule));
    }
}
