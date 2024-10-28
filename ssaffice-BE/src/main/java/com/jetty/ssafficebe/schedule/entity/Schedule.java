package com.jetty.ssafficebe.schedule.entity;

import com.jetty.ssafficebe.user.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    private Long scheduleId;
    private String title;
    private String memo;
    private LocalTime remind;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isEssential;
    @Enumerated(EnumType.STRING)
    private ScheduleCategory category;
    @Enumerated(EnumType.STRING)
    private ScheduleType type;
    private Boolean isFinish;
    private Boolean isEnroll;
    private String url;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "notice_id")
//    private Notice notice;
}
