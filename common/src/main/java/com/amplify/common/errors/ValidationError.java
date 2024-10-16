package com.amplify.common.errors;

import org.springframework.http.HttpStatus;


public class ValidationError extends BaseError {
    
    public ValidationError(String errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
    
    public ValidationError(String errorMessage, Object details) {
        super(HttpStatus.BAD_REQUEST, errorMessage, details);
    }
}
