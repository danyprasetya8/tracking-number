package com.logistics.tracking_number.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCustomerResponse {

  private String id;

  private String name;

  private String code;

  private String slug;
}
