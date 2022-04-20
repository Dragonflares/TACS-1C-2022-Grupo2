package com.probasteReiniciando.TPTACS.controllers.exception;


import com.probasteReiniciando.TPTACS.dto.ApiError;
import com.probasteReiniciando.TPTACS.exceptions.TournamentNotFoundException;
import com.probasteReiniciando.TPTACS.exceptions.UserNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                        .message("Request not found")
                        .timestamp(LocalDateTime.now())
                        .debugMessage(ex.getLocalizedMessage())
                        .path(((ServletWebRequest)request).getRequest().getRequestURI().toString())
                        .status(HttpStatus.BAD_REQUEST).build(),
                headers, HttpStatus.NOT_FOUND, request);
    }



    @ExceptionHandler({UserNotFoundException.class, UsernameNotFoundException.class, TournamentNotFoundException.class})
    public ResponseEntity<ApiError> handleConflict(Exception ex,WebRequest request)
    {

        ApiError error =ApiError.builder().status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(((ServletWebRequest)request).getRequest().getRequestURI().toString())
                .status(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }


}