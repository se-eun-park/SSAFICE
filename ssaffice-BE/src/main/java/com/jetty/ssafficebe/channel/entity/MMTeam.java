package com.jetty.ssafficebe.channel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mm_team")
@Getter
@Setter
public class MMTeam {

    @Id
    private String mmTeamId;
    private String mmTeamName;
}
