package com.logistics.tracking_number.web.controller;

import com.logistics.tracking_number.helper.ResponseHelper;
import com.logistics.tracking_number.service.TrackingNumberService;
import com.logistics.tracking_number.service.model.NextTrackingNumberRequest;
import com.logistics.tracking_number.web.response.BaseResponse;
import com.logistics.tracking_number.web.response.GetTrackingNumberResponse;
import com.logistics.tracking_number.web.response.NextTrackingNumberResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class TrackingController {

  private final TrackingNumberService trackingNumberService;

  public TrackingController(TrackingNumberService trackingNumberService) {
    this.trackingNumberService = trackingNumberService;
  }

  @GetMapping("/tracking-number")
  public Mono<BaseResponse<List<GetTrackingNumberResponse>>> getAllTrackingNumbers() {
    return trackingNumberService.getAllTrackingNumbers()
        .map(ResponseHelper::success);
  }

  @GetMapping("/next-tracking-number")
  public Mono<BaseResponse<NextTrackingNumberResponse>> nextTrackingNumber(@RequestParam("origin_country_id") String originCountryId,
                                                                           @RequestParam("destination_country_id") String destinationCountryId,
                                                                           @RequestParam("weight") Double weight,
                                                                           @RequestParam("created_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt,
                                                                           @RequestParam("customer_id") String customerId,
                                                                           @RequestParam("customer_name") String customerName,
                                                                           @RequestParam("customer_slug") String customerSlug) {

    NextTrackingNumberRequest request = NextTrackingNumberRequest.builder()
        .originCountryId(originCountryId)
        .destinationCountryId(destinationCountryId)
        .weight(weight)
        .createdAt(createdAt)
        .customerId(customerId)
        .customerName(customerName)
        .customerSlug(customerSlug)
        .build();

    return trackingNumberService.nextTrackingNumber(request)
        .map(ResponseHelper::success);
  }
}
