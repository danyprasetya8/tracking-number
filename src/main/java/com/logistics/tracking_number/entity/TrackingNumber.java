package com.logistics.tracking_number.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trackingNumbers")
public class TrackingNumber implements Persistable<String> {

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

  @Override
  public boolean isNew() {
    return createdAt == null;
  }
}
