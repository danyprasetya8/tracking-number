package com.logistics.tracking_number.service;

import com.logistics.tracking_number.web.request.CreateCustomerRequest;
import com.logistics.tracking_number.web.response.GetCustomerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CustomerService {

  Mono<String> createCustomer(CreateCustomerRequest request);

  Mono<List<GetCustomerResponse>> getCustomers();
}
