package com.jetty.ssafficebe.user.entity;

import com.jetty.ssafficebe.common.jpa.BooleanToYNConverter;
import com.jetty.ssafficebe.role.entity.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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

    private String disabledYn = "N";

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "disabledYn", updatable = false, insertable = false)
    private boolean disabled;

    private Integer cohortNum;

    // TODO : 지역 코드 entity 생성 및 연결 필요
    private String regionCd;

    private Integer Class;

    private String roleId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>();


}
