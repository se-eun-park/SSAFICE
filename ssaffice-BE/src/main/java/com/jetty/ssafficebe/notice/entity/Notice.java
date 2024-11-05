package com.jetty.ssafficebe.notice.entity;

import com.jetty.ssafficebe.common.jpa.BooleanToYNConverter;
import com.jetty.ssafficebe.schedule.entity.TaskType;
import com.jetty.ssafficebe.user.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private String mmMessageId;

    private String title;

    @Lob
    private String content;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private String taskTypeCd;

    @Enumerated(EnumType.STRING)
    @Column(name = "taskTypeCd", updatable = false, insertable = false)
    private TaskType taskType;

    private String isEssentialYn = "N";

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "isEssentialYn", updatable = false, insertable = false)
    private Boolean isEssential;

}
