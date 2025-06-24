package com.logistics.tracking_number.web.advice;

import com.logistics.tracking_number.common.exception.BadRequestException;
import com.logistics.tracking_number.helper.ResponseHelper;
import com.logistics.tracking_number.web.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<String> badRequestException(BadRequestException ex) {
    return ResponseHelper.badRequest(ex.getMessage());
  }

  @ExceptionHandler(ServerWebInputException.class)
  public BaseResponse<String> serverWebInputException(ServerWebInputException ex) {
    return ResponseHelper.badRequest(ex.getReason());
  }
}
