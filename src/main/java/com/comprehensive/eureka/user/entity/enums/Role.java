package com.comprehensive.eureka.user.entity.enums;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER("일반사용자"),
    ROLE_ADMIN("관리자");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public Role[] getAllRole() {
        return Role.values();
    }
}
