package com.customer.management.api.service.impl;

import com.customer.management.api.dao.CustomerRequest;
import com.customer.management.api.dao.response.CustomerResponse;
import com.customer.management.api.entity.Customer;
import com.customer.management.api.expection.CustomerNotFoundException;
import com.customer.management.api.expection.EmailAlreadyExistsException;
import com.customer.management.api.mapper.CustomerMapper;
import com.customer.management.api.repository.CustomerRepository;
import com.customer.management.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl
        implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public CustomerResponse create(
            CustomerRequest request) {

        repository.findByEmail(request.email())
                .ifPresent(c -> {
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

        return CustomerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse get(UUID id) {

        Customer customer =
                repository.findById(id)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer not found"));

        return CustomerMapper.toResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAll() {

        return repository.findByDeletedFalse()
                .stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CustomerResponse update(UUID id, CustomerRequest request) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with id: " + id));

        // Check email uniqueness only if email changed
        if (!customer.getEmail().equals(request.email())) {

            repository.findByEmail(request.email())
                    .ifPresent(existingCustomer -> {
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

        return CustomerMapper.toResponse(updatedCustomer);
    }

    @Override
    public void delete(UUID id) {

        Customer customer =
                repository.findById(id)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer not found"));

        customer.setDeleted(true);
    }
}