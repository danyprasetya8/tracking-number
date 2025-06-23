package com.logistics.tracking_number.web.controller;

import com.logistics.tracking_number.web.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1")
public class TrackingController {

  @GetMapping("/hello")
  public Mono<BaseResponse<Boolean>> test() {
    return Mono.just(BaseResponse.<Boolean>builder()
        .data(true)
        .build());
  }
}
