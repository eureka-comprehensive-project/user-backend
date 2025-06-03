package com.comprehensive.eureka.user.entity;

import com.comprehensive.eureka.user.entity.enums.Role;
import com.comprehensive.eureka.user.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // user 테이블의 pk 값
    private String email; // 아이디에 사용될 값
    private String name; // 사용자 이름
    private String password; // 비밀번호 (암호화된 값)
    private Integer age; // 나이
    private Boolean isGuardian; // 보호자 여부
    @Enumerated(EnumType.STRING)
    private Role role; // 역할 (일반사용자, 관리자)
    @Enumerated(EnumType.STRING)
    private Status status; // 사용 여부 (사용 가능, 사용 불가)
}
