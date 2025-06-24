package com.logistics.tracking_number.repository;

import com.logistics.tracking_number.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

  Mono<Customer> findByCode(String code);
}
