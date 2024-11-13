package com.jetty.ssafficebe.channel.entity;

import com.jetty.ssafficebe.notice.entity.Notice;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserChannel> userChannels = new ArrayList<>();
}
