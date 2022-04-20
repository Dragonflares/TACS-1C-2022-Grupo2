package com.probasteReiniciando.TPTACS.controllers.exception;


import com.probasteReiniciando.TPTACS.dto.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class, MethodArgumentTypeMismatchException.class, RuntimeException.class, })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ApiError.builder().status(HttpStatus.BAD_REQUEST)
                        .message("Request malformed")
                        .timestamp(LocalDateTime.now())
                        .debugMessage(ex.getLocalizedMessage())
                        .path(((ServletWebRequest)request).getRequest().getRequestURI().toString())
                        .status(HttpStatus.BAD_REQUEST).build(),
                headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, ApiError.builder().status(HttpStatus.NOT_FOUND)
                        .message("Request malformed")
                        .timestamp(LocalDateTime.now())
                        .debugMessage(ex.getLocalizedMessage())
                        .path(((ServletWebRequest)request).getRequest().getRequestURI().toString())
                        .status(HttpStatus.BAD_REQUEST).build(),
                headers, HttpStatus.BAD_REQUEST, request);
    }


}