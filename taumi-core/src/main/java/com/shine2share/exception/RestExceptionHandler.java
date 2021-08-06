package com.shine2share.exception;

import com.shine2share.common.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleAllException(Exception e) {
        return handleAllExceptionImpl(e);
    }
    private ResponseEntity<String> handleAllExceptionImpl(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            logger.error(String.format("Business Exception: code=%s, message=%s", businessException.getCode(), businessException.getMessage()));
            return new ResponseEntity<>(businessException.getMessage(), businessException.getHttpStatus());
        } else if (e instanceof InvalidTokenException) {
            logger.error(String.format("Unhandled Exception: fullstack=%s", e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        else {
            logger.error(String.format("Unhandled Exception: fullstack=%s", e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
