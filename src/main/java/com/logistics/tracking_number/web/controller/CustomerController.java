package com.logistics.tracking_number.web.controller;

import com.logistics.tracking_number.helper.ResponseHelper;
import com.logistics.tracking_number.service.CustomerService;
import com.logistics.tracking_number.web.request.CreateCustomerRequest;
import com.logistics.tracking_number.web.response.BaseResponse;
import com.logistics.tracking_number.web.response.GetCustomerResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/customer")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping
  public Mono<BaseResponse<String>> createCustomer(@RequestBody CreateCustomerRequest request) {
    return customerService.createCustomer(request)
        .map(ResponseHelper::success);
  }

  @GetMapping
  public Mono<BaseResponse<List<GetCustomerResponse>>> getAllCustomer() {
    return customerService.getCustomers()
        .map(ResponseHelper::success);
  }
}
