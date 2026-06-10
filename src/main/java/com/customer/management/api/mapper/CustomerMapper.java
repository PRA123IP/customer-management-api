package com.customer.management.api.mapper;


import com.customer.management.api.dao.response.CustomerResponse;
import com.customer.management.api.entity.Customer;

public class CustomerMapper {

    private CustomerMapper() {
    }

    public static CustomerResponse toResponse(
            Customer customer) {

        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getDateOfBirth(),
                customer.getAddress(),
                customer.getAccountStatus(),
                customer.getCreditScore(),
                customer.getCreatedAt()
        );
    }
}