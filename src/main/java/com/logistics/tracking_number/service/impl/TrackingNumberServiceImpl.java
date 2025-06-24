package com.logistics.tracking_number.service.impl;

import com.logistics.tracking_number.common.exception.BadRequestException;
import com.logistics.tracking_number.entity.Customer;
import com.logistics.tracking_number.entity.TrackingNumber;
import com.logistics.tracking_number.repository.CustomerRepository;
import com.logistics.tracking_number.repository.TrackingNumberRepository;
import com.logistics.tracking_number.service.RedisService;
import com.logistics.tracking_number.service.TrackingNumberService;
import com.logistics.tracking_number.service.model.NextTrackingNumberRequest;
import com.logistics.tracking_number.web.response.GetTrackingNumberResponse;
import com.logistics.tracking_number.web.response.NextTrackingNumberResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class TrackingNumberServiceImpl implements TrackingNumberService {

  private static final Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());

  private static final String PREFIX = "TX";

  private final RedisService redisService;

  private final TrackingNumberRepository trackingNumberRepository;

  private final CustomerRepository customerRepository;

  public TrackingNumberServiceImpl(RedisService redisService, TrackingNumberRepository trackingNumberRepository, CustomerRepository customerRepository) {
    this.redisService = redisService;
    this.trackingNumberRepository = trackingNumberRepository;
    this.customerRepository = customerRepository;
  }

  @Override
  public Mono<List<GetTrackingNumberResponse>> getAllTrackingNumbers() {
    return trackingNumberRepository.findAll()
        .map(entity -> GetTrackingNumberResponse.builder()
            .id(entity.getId())
            .originCountryId(entity.getOriginCountryId())
            .destinationCountryId(entity.getDestinationCountryId())
            .weight(entity.getWeight())
            .customerId(entity.getCustomerId())
            .customerName(entity.getCustomerName())
            .customerSlug(entity.getCustomerSlug())
            .createdAt(Optional.ofNullable(entity.getCreatedAt())
                .map(Instant::toString)
                .orElse(""))
            .build())
        .collectList();
  }

  @Override
  public Mono<NextTrackingNumberResponse> nextTrackingNumber(NextTrackingNumberRequest request) {
    validateRequest(request);
    return customerRepository.findById(request.getCustomerId())
        .switchIfEmpty(Mono.error(() -> new BadRequestException("customer not exist")))
        .map(Customer::getCode)
        .flatMap(customerCode -> generate(customerCode, request))
        .flatMap(trackingNumber -> saveDocument(trackingNumber, request))
        .map(this::toResponse);
  }

  private Mono<String> generate(String customerCode, NextTrackingNumberRequest request) {
    return Mono.fromSupplier(() -> preGenerate(customerCode, request.getCreatedAt()))
        .flatMap(pre -> redisService.incrementSequence(pre, Duration.ofDays(1))
            .map(sequence -> finalize(pre, sequence)));
  }

  private Mono<TrackingNumber> saveDocument(String trackingNumber, NextTrackingNumberRequest request) {
    return trackingNumberRepository.save(toEntity(trackingNumber, request))
        .onErrorResume(error -> Mono.error(new BadRequestException(error.getMessage())));
  }

  private void validateRequest(NextTrackingNumberRequest request) {
    if (!ISO_COUNTRIES.contains(request.getOriginCountryId())) {
      throw new BadRequestException("originCountryId must be a valid country code");
    }
    if (!ISO_COUNTRIES.contains(request.getDestinationCountryId())) {
      throw new BadRequestException("destinationCountryId must be a valid country code");
    }
    if (!isValidWeight(request.getWeight())) {
      throw new BadRequestException("weight must be at max 3 decimal places");
    }
  }

  private boolean isValidWeight(Double weight) {
    String[] parts = weight.toString().split("\\.");
    return parts.length > 0 && parts[1].length() <= 3;
  }

  private String formatDate(LocalDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("ddMMyy"));
  }

  private String preGenerate(String customerCode, LocalDateTime createdAt) {
    return PREFIX + customerCode + formatDate(createdAt);
  }

  private String finalize(String pre, Long sequence) {
    return pre + String.format("%06d", sequence);
  }

  private TrackingNumber toEntity(String trackingNumber, NextTrackingNumberRequest request) {
    return TrackingNumber.builder()
        .id(trackingNumber)
        .originCountryId(request.getOriginCountryId())
        .destinationCountryId(request.getDestinationCountryId())
        .weight(request.getWeight())
        .customerId(request.getCustomerId())
        .customerName(request.getCustomerName())
        .customerSlug(request.getCustomerSlug())
        .build();
  }

  private NextTrackingNumberResponse toResponse(TrackingNumber entity) {
    return NextTrackingNumberResponse.builder()
        .trackingNumber(entity.getId())
        .createdAt(Optional.ofNullable(entity.getCreatedAt())
            .map(Instant::toString)
            .orElse(""))
        .build();
  }
}
