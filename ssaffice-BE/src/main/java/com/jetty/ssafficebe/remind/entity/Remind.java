package com.jetty.ssafficebe.remind.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jetty.ssafficebe.common.jpa.BooleanToYNConverter;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "remind")
public class Remind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long remindId;

    private String isEssentialYn = "N";
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "isEssentialYn", updatable = false, insertable = false)
    private Boolean isEssential;

    private LocalDateTime remindDateTime;

    private Long scheduleId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId", insertable = false, updatable = false)
    private Schedule schedule;
}
