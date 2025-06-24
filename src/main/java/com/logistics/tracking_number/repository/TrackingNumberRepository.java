package com.logistics.tracking_number.repository;

import com.logistics.tracking_number.entity.TrackingNumber;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TrackingNumberRepository extends ReactiveMongoRepository<TrackingNumber, String> {
}
