package com.jetty.ssafficebe.channel.entity;

import com.jetty.ssafficebe.notice.entity.Notice;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notice_channel")
@Getter
@Setter
public class NoticeChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeChannelId;

    private String channelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channelId", insertable = false, updatable = false)
    private Channel channel;

    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noticeId", insertable = false, updatable = false)
    private Notice notice;

}
