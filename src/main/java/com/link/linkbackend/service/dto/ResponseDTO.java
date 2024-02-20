package com.link.linkbackend.service.dto;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    private int status;
    private String message;
    private T data;

    public ResponseDTO(T data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public ResponseDTO(T data, String message) {
        this.data = data;
        this.message = message;
        this.status = 200;
    }

}
