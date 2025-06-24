package com.logistics.tracking_number.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
public class TrackingNumber {

  @Id
  private String id;

  private String originCountryId;

  private String destinationCountryId;

  private Double weight;

  private String customerId;

  private String customerName;

  private String customerSlug;

  @CreatedDate
  private Instant createdAt;

  @LastModifiedDate
  private Instant updatedAt;
}
