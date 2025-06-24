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
public class NextTrackingNumberResponse {

  @JsonProperty("tracking_number")
  private String trackingNumber;

  @JsonProperty("created_at")
  private Integer createdAt;
}
