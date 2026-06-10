package com.customer.management.api.service;

import com.customer.management.api.dao.request.CustomerRequest;
import com.customer.management.api.dao.response.CustomerResponse;
import org.springframework.data.domain.*;
import java.util.UUID;

public interface CustomerService {

    CustomerResponse create(CustomerRequest request);

    CustomerResponse get(UUID id);

    Page<CustomerResponse> getAll(Pageable pageable);

    CustomerResponse update(UUID id,
                            CustomerRequest request);

    void delete(UUID id);
}