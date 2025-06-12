package com.comprehensive.eureka.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_plan_record")
public class UserPlanRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_plan_record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "plan_benefit_id", nullable = false)
    private Long planBenefitId;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "is_active")
    private Boolean isActive;

}
