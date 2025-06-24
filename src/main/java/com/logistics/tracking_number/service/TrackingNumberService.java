package com.logistics.tracking_number.service;

import com.logistics.tracking_number.service.model.NextTrackingNumberRequest;
import com.logistics.tracking_number.web.response.NextTrackingNumberResponse;
import reactor.core.publisher.Mono;

public interface TrackingNumberService {

  Mono<NextTrackingNumberResponse> nextPublicNumber(NextTrackingNumberRequest request);
}
