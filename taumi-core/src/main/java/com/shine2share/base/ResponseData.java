package com.shine2share.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

public class ResponseData<T> implements Serializable {
    private int code;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private Date timestamp;

    private String uuid;

    private T data;

    public ResponseData() {
        this.timestamp = new Date();
    }

    public ResponseData<T> success(T data) {
        this.data = data;
        this.code = 0;
        this.message = "Success!";
        this.uuid = "";
        return this;
    }

    public ResponseData<T> success(String uuid, T data) {
        this.data = data;
        this.code = 0;
        this.message = "Success!";
        this.uuid = uuid;
        return this;
    }

    public ResponseData<T> error(int code, String message, String uuid) {
        this.code = code;
        this.message = message;
        this.uuid = uuid;
        return this;
    }

    public ResponseData<T> error(int code, String message, String uuid, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.uuid = uuid;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUuid() { return uuid; }
}
