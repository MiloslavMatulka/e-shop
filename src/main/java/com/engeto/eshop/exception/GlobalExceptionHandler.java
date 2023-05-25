package com.engeto.eshop.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${com.engeto.author}")
    private String nameOfAuthor;

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception e,
                                                         WebRequest request) {
        logger.info("Error occurred: " + e.getLocalizedMessage() + ", "
                + request.getDescription(false));
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                nameOfAuthor, e.getLocalizedMessage(),
                request.getDescription(false), e.getStackTrace());
        return new ResponseEntity<>(errorResponse,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
