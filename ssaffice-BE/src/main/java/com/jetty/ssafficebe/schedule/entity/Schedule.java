package com.jetty.ssafficebe.schedule.entity;

import com.jetty.ssafficebe.schedule.code.ScheduleSourceType;
import com.jetty.ssafficebe.schedule.code.ScheduleStatusType;
import com.jetty.ssafficebe.common.jpa.BooleanToYNConverter;
import com.jetty.ssafficebe.notice.entity.Notice;
import com.jetty.ssafficebe.remind.entity.Remind;
import com.jetty.ssafficebe.user.entity.BaseEntity;
import com.jetty.ssafficebe.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String title;
    private String memo;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String scheduleSourceTypeCd;

    @Enumerated(EnumType.STRING)
    @Column(name = "scheduleSourceTypeCd", updatable = false, insertable = false)
    private ScheduleSourceType scheduleSourceType;

    private String scheduleStatusTypeCd = "TODO";

    @Enumerated(EnumType.STRING)
    @Column(name = "scheduleStatusTypeCd", updatable = false, insertable = false)
    private ScheduleStatusType scheduleStatusType;

    private String isEssentialYn = "N";

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "isEssentialYn", updatable = false, insertable = false)
    private Boolean isEssential;

    private String isEnrollYn = "N";

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "isEnrollYn", updatable = false, insertable = false)
    private Boolean isEnroll;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noticeId", insertable = false, updatable = false)
    private Notice notice;

    @OneToMany(mappedBy = "scheduleId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Remind> remindList = new ArrayList<>();

    public void addRemind(Remind remind) {
        this.remindList.add(remind);
        remind.setSchedule(this);
    }
}
