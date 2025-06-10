package com.comprehensive.eureka.user.entity;

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
@Table(name = "user_data_record")
public class UserDataRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_data_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "data_usage")
    private Integer dataUsage;

    @Column(name = "data_usage_unit", length = 2)
    private String dataUsageUnit;

    @Column(name = "message_usage")
    private Integer messageUsage;

    @Column(name = "call_usage")
    private Integer callUsage;

    @Column(name = "year_month_value", length = 10)
    private String yearMonth; // 형식: "2025-06"
}
