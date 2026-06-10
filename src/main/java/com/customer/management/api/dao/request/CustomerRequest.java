package com.customer.management.api.dao.request;

import com.customer.management.api.constans.AccountStatus;
import jakarta.validation.constraints.*;


import java.time.LocalDate;

public record CustomerRequest(

        @NotBlank String firstName,

        @NotBlank String lastName,

        @Email String email,

        String phoneNumber,

        @NotNull LocalDate dateOfBirth,

        String address,

        AccountStatus accountStatus,

        @Min(300)
        @Max(850)
        Integer creditScore
) {}