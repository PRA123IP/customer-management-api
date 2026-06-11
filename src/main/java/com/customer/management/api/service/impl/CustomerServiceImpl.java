package com.customer.management.api.service.impl;

import com.customer.management.api.dao.request.CustomerRequest;
import com.customer.management.api.dao.response.CustomerResponse;
import com.customer.management.api.entity.Customer;
import com.customer.management.api.exception.CustomerNotFoundException;
import com.customer.management.api.exception.EmailAlreadyExistsException;
import com.customer.management.api.mapper.CustomerMapper;
import com.customer.management.api.repository.CustomerRepository;
import com.customer.management.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerServiceImpl
        implements CustomerService {

    private final CustomerRepository repository;

    private final CustomerMapper mapper;

    @Override
    public CustomerResponse create(
            CustomerRequest request) {
        log.info("Creating customer with email={}", request.email());
        repository.findByEmail(request.email())
                .ifPresent(c -> {
                    log.warn("Duplicate email attempt: {}", request.email());
                    throw new EmailAlreadyExistsException(
                            "Email already exists");
                });

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .dateOfBirth(request.dateOfBirth())
                .address(request.address())
                .accountStatus(request.accountStatus())
                .creditScore(request.creditScore())
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(customer);
        log.info("Customer created successfully with id={}", customer.getId());

        return mapper.toResponse(customer);
    }

    @Override
    public CustomerResponse get(UUID id) {

        log.info("Fetching customer by id={}", id);

        Customer customer =
                repository.findByIdAndDeletedFalse(id)
                        .orElseThrow(() -> {
                            log.warn("Customer not found id={}", id);
                            return new CustomerNotFoundException("Customer not found");
                        });

        log.debug("Customer found email={}", customer.getEmail());

        return mapper.toResponse(customer);
    }

    @Override
    public Page<CustomerResponse> getAll(Pageable pageable) {

        log.info("Fetching customers page={} size={}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<CustomerResponse> result =
                repository.findByDeletedFalse(pageable)
                        .map(mapper::toResponse);

        log.info("Fetched {} customers",
                result.getTotalElements());

        return result;
    }




    @Override
    @Transactional
    public CustomerResponse update(UUID id, CustomerRequest request) {

        log.info("Updating customer id={}", id);

        Customer customer = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Update failed - customer not found id={}", id);
                    return new CustomerNotFoundException(
                            "Customer not found with id: " + id);
                });

        if (!customer.getEmail().equals(request.email())) {

            log.info("Email change detected for id={} oldEmail={} newEmail={}",
                    id, customer.getEmail(), request.email());

            repository.findByEmail(request.email())
                    .ifPresent(existing -> {
                        log.warn("Email already exists: {}", request.email());
                        throw new EmailAlreadyExistsException(
                                "Customer already exists with email: "
                                        + request.email());
                    });
        }

        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPhoneNumber(request.phoneNumber());
        customer.setDateOfBirth(request.dateOfBirth());
        customer.setAddress(request.address());
        customer.setAccountStatus(request.accountStatus());
        customer.setCreditScore(request.creditScore());

        Customer updatedCustomer = repository.save(customer);

        log.info("Customer updated successfully id={}", id);

        return mapper.toResponse(updatedCustomer);
    }


    @Override
    public void delete(UUID id) {
        log.info("Deleting customer id={}", id);
        Customer customer = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        customer.setDeleted(true);
        log.info("Customer soft deleted id={}", id);
        repository.save(customer);
    }
}