package com.customer.management.api.dao.response;

import com.customer.management.api.constans.AccountStatus;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponse(

        UUID id,

        String firstName,

        String lastName,

        String email,

        String phoneNumber,

        LocalDate dateOfBirth,

        String address,

        AccountStatus accountStatus,

        Integer creditScore,

        LocalDateTime createdAt
)
{
}