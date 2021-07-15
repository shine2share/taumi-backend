package com.shine2share.auth.enums;
import com.shine2share.common.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum ErrorCode implements BaseErrorCode {
    USER_NOT_ACCEPT(1, "user.not.accept", HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_REQUIRED(2, "refresh.token.required", HttpStatus.UNPROCESSABLE_ENTITY),
    REFRESH_TOKEN_NOT_EXIST(3, "refresh.token.not.exist", HttpStatus.UNPROCESSABLE_ENTITY),
    REFRESH_TOKEN_EXPIRED(4, "refresh.token.expired", HttpStatus.UNPROCESSABLE_ENTITY),
    GRANT_TYPE_NOT_SUPPORTED(5, "grant.type.not.supported", HttpStatus.UNPROCESSABLE_ENTITY),
    USER_LOCKED(6, "user.locked", HttpStatus.UNPROCESSABLE_ENTITY),
    USER_NOT_FOUND(7, "process.init.error", HttpStatus.UNPROCESSABLE_ENTITY),
    TOKEN_INVALID(8, "token.invalid", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(9, "token.expired", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(10, "access.denied", HttpStatus.FORBIDDEN),
    USER_PASS_INVALID(11, "user.pass.invalid", HttpStatus.BAD_REQUEST),
    SERVER_ERROR(13, "server.error", HttpStatus.UNPROCESSABLE_ENTITY),
    WRONG_PASSWORD(1011, "wrong.password", HttpStatus.UNAUTHORIZED),
    CHECK_AUTHENTICATION(14, "check.authentication", HttpStatus.UNAUTHORIZED);

    private int code;
    private String message;
    private HttpStatus httpStatus;
    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
