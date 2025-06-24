package com.logistics.tracking_number.service.impl;

import com.logistics.tracking_number.service.RedisService;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

@Service
public class RedisServiceImpl implements RedisService {

  private final ReactiveStringRedisTemplate redisTemplate;


  public RedisServiceImpl(ReactiveStringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public Mono<Long> incrementSequence(String key, Duration duration) {
    Duration d = Optional.of(duration)
        .orElse(Duration.ofDays(1));

    return redisTemplate.opsForValue()
        .increment(key)
        .flatMap(seq -> redisTemplate.expire(key, d)
            .thenReturn(seq));
  }
}
