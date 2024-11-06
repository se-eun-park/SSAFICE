package com.jetty.ssafficebe.user.entity;

import com.jetty.ssafficebe.common.jpa.BooleanToYNConverter;
import com.jetty.ssafficebe.role.entity.UserRole;
import com.jetty.ssafficebe.schedule.entity.Schedule;
import com.jetty.ssafficebe.user.code.Region;
import com.jetty.ssafficebe.user.code.Track;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;

    private String password;

    private String name;

    private String isDisabledYn = "N";

    private String profileImgUrl;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "isDisabledYn", updatable = false, insertable = false)
    private boolean isDisabled;

    private Integer cohortNum;

    private String trackCd;

    @Enumerated(EnumType.STRING)
    @Column(name = "trackCd", updatable = false, insertable = false)
    private Track track;

    private String regionCd;

    @Enumerated(EnumType.STRING)
    @Column(name = "regionCd", updatable = false, insertable = false)
    private Region region;

    private Integer classNum;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserRole> userRoles = new ArrayList<>();

}
