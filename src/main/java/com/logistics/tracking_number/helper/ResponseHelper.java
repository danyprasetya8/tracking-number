package com.logistics.tracking_number.helper;

import com.logistics.tracking_number.web.response.BaseResponse;

public class ResponseHelper {

  public static <T> BaseResponse<T> badRequest(String message) {
    return BaseResponse.<T>builder()
        .code(400)
        .status("failed")
        .error(message)
        .build();
  }

  public static <T> BaseResponse<T> success(T data) {
    return BaseResponse.<T>builder()
        .code(200)
        .status("success")
        .data(data)
        .build();
  }
}
