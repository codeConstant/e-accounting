package com.amplify.common.errors;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private Object error;
    private String timestamp;
}
