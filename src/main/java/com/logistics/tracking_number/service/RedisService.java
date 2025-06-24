package com.logistics.tracking_number.service;

import reactor.core.publisher.Mono;

import java.time.Duration;

public interface RedisService {

  Mono<Long> incrementSequence(String key, Duration duration);
}
