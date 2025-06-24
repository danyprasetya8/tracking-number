package com.logistics.tracking_number.service.impl;

import com.logistics.tracking_number.common.exception.BadRequestException;
import com.logistics.tracking_number.service.TrackingNumberService;
import com.logistics.tracking_number.service.model.NextTrackingNumberRequest;
import com.logistics.tracking_number.web.response.NextTrackingNumberResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TrackingNumberServiceImpl implements TrackingNumberService {

  @Override
  public Mono<NextTrackingNumberResponse> nextTrackingNumber(NextTrackingNumberRequest request) {
    return Mono.error(() -> new BadRequestException("testing bro"));
  }
}
