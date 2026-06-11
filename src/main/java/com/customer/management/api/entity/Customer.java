package com.customer.management.api.entity;

import com.customer.management.api.constans.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    private String address;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Min(300)
    @Max(850)
    private Integer creditScore;

    private LocalDateTime createdAt;

    private boolean deleted;
}