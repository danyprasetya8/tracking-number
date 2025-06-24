package com.logistics.tracking_number.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NextTrackingNumberRequest {

  private String originCountryId;

  private String destinationCountryId;

  private Double weight;

  private LocalDateTime createdAt;

  private String customerId;

  private String customerName;

  private String customerSlug;
}
