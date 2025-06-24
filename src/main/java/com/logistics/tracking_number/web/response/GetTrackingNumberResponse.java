package com.logistics.tracking_number.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTrackingNumberResponse {

  private String id;

  @JsonProperty("origin_country_id")
  private String originCountryId;

  @JsonProperty("destination_country_id")
  private String destinationCountryId;

  private Double weight;

  @JsonProperty("customer_id")
  private String customerId;

  @JsonProperty("customer_name")
  private String customerName;

  @JsonProperty("customer_slug")
  private String customerSlug;

  @JsonProperty("created_at")
  private String createdAt;
}
