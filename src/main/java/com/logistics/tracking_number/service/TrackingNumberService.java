package com.logistics.tracking_number.service;

import com.logistics.tracking_number.service.model.NextTrackingNumberRequest;
import com.logistics.tracking_number.web.response.GetTrackingNumberResponse;
import com.logistics.tracking_number.web.response.NextTrackingNumberResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TrackingNumberService {

  Mono<List<GetTrackingNumberResponse>> getAllTrackingNumbers();

  Mono<NextTrackingNumberResponse> nextTrackingNumber(NextTrackingNumberRequest request);
}
