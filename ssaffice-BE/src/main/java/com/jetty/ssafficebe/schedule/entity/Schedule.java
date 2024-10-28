package com.jetty.ssafficebe.schedule.entity;

import com.jetty.ssafficebe.user.entity.BaseEntity;
import com.jetty.ssafficebe.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    private Long scheduleId;
    private String title;
    private String memo;
    private String remind;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isEssential;
    private String category; // "일반", "파일"
    private String type; // "공지", "개인"
    private Boolean isFinish;
    private Boolean isEnroll;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "notice_id")
//    private Notice notice;
}
