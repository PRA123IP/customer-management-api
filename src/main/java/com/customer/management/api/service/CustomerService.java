package com.customer.management.api.service;

import com.customer.management.api.dao.CustomerRequest;
import com.customer.management.api.dao.response.CustomerResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerResponse create(CustomerRequest request);

    CustomerResponse get(UUID id);

    List<CustomerResponse> getAll();

    CustomerResponse update(UUID id,
                            CustomerRequest request);

    void delete(UUID id);
}