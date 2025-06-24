package com.logistics.tracking_number.service.impl;

import com.logistics.tracking_number.common.exception.BadRequestException;
import com.logistics.tracking_number.entity.Customer;
import com.logistics.tracking_number.repository.CustomerRepository;
import com.logistics.tracking_number.service.CustomerService;
import com.logistics.tracking_number.web.request.CreateCustomerRequest;
import com.logistics.tracking_number.web.response.GetCustomerResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Mono<String> createCustomer(CreateCustomerRequest request) {
    return customerRepository.findByCode(request.getCode())
        .flatMap($ -> Mono.error(new BadRequestException("customer code already exist")))
        .map($ -> "")
        .switchIfEmpty(Mono.defer(() -> customerRepository.save(toEntity(request)))
            .map(Customer::getId));
  }

  private Customer toEntity(CreateCustomerRequest request) {
    return Customer.builder()
        .id(UUID.randomUUID().toString())
        .code(request.getCode())
        .name(request.getName())
        .slug(request.getSlug())
        .build();
  }

  @Override
  public Mono<List<GetCustomerResponse>> getCustomers() {
    return customerRepository.findAll()
        .map(customer -> GetCustomerResponse.builder()
            .id(customer.getId())
            .name(customer.getName())
            .code(customer.getCode())
            .slug(customer.getSlug())
            .build())
        .collectList();
  }
}
