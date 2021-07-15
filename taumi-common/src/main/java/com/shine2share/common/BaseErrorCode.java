package com.shine2share.common;
import org.springframework.http.HttpStatus;
public interface BaseErrorCode {
    int getCode();
    String getMessage();
    HttpStatus getHttpStatus();
}
