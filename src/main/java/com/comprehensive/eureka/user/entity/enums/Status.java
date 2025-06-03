package com.comprehensive.eureka.user.entity.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("사용가능"),
    INACTIVE("사용불가");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public Status[] getAllStatus() {
        return Status.values();
    }
}
