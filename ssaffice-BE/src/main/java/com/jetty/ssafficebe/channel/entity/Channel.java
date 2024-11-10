package com.jetty.ssafficebe.channel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "channel")
@Getter
@Setter
public class Channel {

    @Id
    private String channelId;
    private String channelName;
    private String mmTeamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mmTeamId", insertable = false, updatable = false)
    private MMTeam mmTeam;
}
