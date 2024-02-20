package com.link.linkbackend.service.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class BadRequestException extends RuntimeException {
    private HttpStatus status;
    private String message;
    private Throwable cause;

    public BadRequestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public BadRequestException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
    }
}
